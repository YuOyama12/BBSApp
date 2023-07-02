package com.yuoyama12.bbsapp.ui.login

import android.app.Activity
import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.yuoyama12.bbsapp.R
import com.yuoyama12.bbsapp.UserAccountInfoValidation
import com.yuoyama12.bbsapp.composable.*
import com.yuoyama12.bbsapp.composable.component.OnExecutingIndicator
import com.yuoyama12.bbsapp.data.UserAccount
import com.yuoyama12.bbsapp.ui.theme.BBSAppTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    isOnTop: Boolean,
    moveToMainScreen: () -> Unit,
    onCreateAccountClicked: () -> Unit,
    popBackStack: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: LoginViewModel = hiltViewModel()
    val onLoginExecuting by viewModel.onLoginExecuting.collectAsState(false)

    var userAccount by rememberSaveable { mutableStateOf(UserAccount()) }

    var openConfirmationDialog by remember { mutableStateOf(false) }

    Column {
        NormalTopAppBar(
            text = stringResource(R.string.login_screen_app_bar_title)
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailField(
                modifier = userAccountFieldModifier,
                value = userAccount.email,
                onValueChanged = { userAccount = userAccount.copy(email = it) }
            )

            PasswordField(
                modifier = userAccountFieldModifier,
                value = userAccount.password,
                onValueChanged = { userAccount = userAccount.copy(password = it) },
                placeholder = stringResource(R.string.password_placeholder)
            )

            Button(
                onClick = {
                    val accountInfoValidation = UserAccountInfoValidation(context)

                    if (accountInfoValidation.isInputtedInfoValidForLogin(userAccount)) {
                        viewModel.login(
                            userAccount = userAccount,
                            onTaskCompleted = {
                                Toast.makeText(
                                    context,
                                    R.string.login_completed_message,
                                    Toast.LENGTH_SHORT
                                ).show()

                                moveToMainScreen()
                            },
                            onTaskFailed = {  }
                        )
                    }
                },
                modifier = userAccountButtonModifier,
            ) {
                Text(
                    text = stringResource(R.string.login_button),
                    fontSize = userAccountButtonFontSize
                )
            }

            Button(
                onClick = { onCreateAccountClicked() },
                modifier = userAccountButtonModifier,
            ) {
                Text(
                    text = stringResource(R.string.sign_up_button),
                    fontSize = userAccountButtonFontSize
                )
            }

            TextButton(
                onClick = {
                    viewModel.loginAsAnonymous(
                        onTaskCompleted = { moveToMainScreen() },
                        onTaskFailed = {  }
                    )
                },
                modifier = userAccountButtonModifier,
            ) {
                Text(
                    text = stringResource(R.string.login_as_guest_button),
                    fontSize = userAccountButtonFontSize
                )
            }
        }
    }

    BackHandler(userAccount.isNotEmpty()) {
        openConfirmationDialog = true
    }

    if (onLoginExecuting) {
        OnExecutingIndicator(text = stringResource(R.string.login_execute_message))
    }

    if (openConfirmationDialog) {
        ConfirmationDialog(
            title = stringResource(R.string.discard_data_confirmation_dialog_title),
            message = stringResource(R.string.discard_data_confirmation_dialog_message),
            positiveButtonText = stringResource(R.string.discard_data_confirmation_dialog_positive_button_text),
            onDismissRequest = { openConfirmationDialog = false },
            onPositiveClicked = {
                if (isOnTop) (context as Activity).finish()
                else popBackStack()
            }
        )
    }

}

@Composable
@Preview(name = "normalMode", showBackground = true)
@Preview(name = "darkMode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
fun LoginScreenPreview() {
    BBSAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            LoginScreen(
                isOnTop = true,
                moveToMainScreen = {},
                onCreateAccountClicked = {},
                popBackStack = {}
            )
        }
    }
}
