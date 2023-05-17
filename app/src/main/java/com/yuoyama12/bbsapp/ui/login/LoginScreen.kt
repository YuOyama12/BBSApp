package com.yuoyama12.bbsapp.ui.login

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    moveToMainScreen: () -> Unit,
    onCreateAccountClicked: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: LoginViewModel = hiltViewModel()
    val onLoginExecuting by viewModel.onLoginExecuting.collectAsState(false)

    NormalTopAppBar(
        text = stringResource(R.string.login_screen_app_bar_title)
    )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val userAccount = remember { mutableStateOf(UserAccount()) }

        EmailField(
            modifier = userAccountFieldModifier,
            value = userAccount.value.email,
            onValueChanged = { userAccount.value = userAccount.value.copy(email = it) }
        )

        PasswordField(
            modifier = userAccountFieldModifier,
            value = userAccount.value.password,
            onValueChanged = { userAccount.value = userAccount.value.copy(password = it) },
            placeholder = stringResource(R.string.password_placeholder)
        )

        Button(
            onClick = {
                val accountInfoValidation = UserAccountInfoValidation(context)

                if (accountInfoValidation.isInputtedInfoValidForLogin(userAccount.value)) {
                    viewModel.login(
                        userAccount = userAccount.value,
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

    if (onLoginExecuting) {
        OnExecutingIndicator(text = stringResource(R.string.login_execute_message))
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
                moveToMainScreen = {},
                onCreateAccountClicked = {}
            )
        }
    }
}
