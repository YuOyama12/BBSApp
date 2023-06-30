package com.yuoyama12.bbsapp.ui.signup

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun SignUpScreen(
    modifier: Modifier = Modifier,
    moveToMainScreen: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: SignUpViewModel = hiltViewModel()
    val onSignUpExecuting by viewModel.onSignUpExecuting.collectAsState(false)

    Column {
        NormalTopAppBar(
            text = stringResource(R.string.sign_up_screen_app_bar_title)
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
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

            PasswordField(
                modifier = userAccountFieldModifier,
                value = userAccount.value.repeatedPassword,
                onValueChanged = { userAccount.value = userAccount.value.copy(repeatedPassword = it) },
                placeholder = stringResource(R.string.repeated_password_placeholder)
            )

            UserNameField(
                modifier = userAccountFieldModifier,
                value = userAccount.value.userName,
                onValueChanged = { userAccount.value = userAccount.value.copy(userName = it) }
            )

            Button(
                onClick = {
                    val accountInfoValidation = UserAccountInfoValidation(context)

                    if (accountInfoValidation.isInputtedInfoValidForSignUp(userAccount.value)) {
                        viewModel.createNewAccount(
                            userAccount = userAccount.value,
                            onTaskCompleted = { moveToMainScreen() },
                            onTaskFailed = {  }
                        )
                    }
                },
                modifier = userAccountButtonModifier,
            ) {
                Text(
                    text = stringResource(R.string.sign_up_button),
                    fontSize = userAccountButtonFontSize
                )
            }
        }
    }

    if (onSignUpExecuting) {
        OnExecutingIndicator(text = stringResource(R.string.sign_up_execute_message))
    }

}

@Composable
@Preview(name = "normalMode", showBackground = true)
@Preview(name = "darkMode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
fun SignUpScreenPreview() {
    BBSAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            SignUpScreen(
                moveToMainScreen ={  }
            )
        }
    }
}