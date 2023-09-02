package com.yuoyama12.bbsapp.ui.verifypassword

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yuoyama12.bbsapp.R
import com.yuoyama12.bbsapp.composable.*
import com.yuoyama12.bbsapp.composable.component.OnExecutingIndicator
import com.yuoyama12.bbsapp.data.FirebaseErrorState

@Composable
fun VerifyPasswordScreen(
    displayedMessage: String,
    onVerifySuccess: () -> Unit
) {
    val viewModel: VerifyPasswordViewModel = hiltViewModel()
    var password by remember { mutableStateOf("") }

    val onVerifyExecuting by viewModel.onVerifyExecuting.collectAsState()
    var verificationErrorState by rememberSaveable { mutableStateOf(FirebaseErrorState()) }

    Column {
        NormalTopAppBar(
            text = stringResource(R.string.verify_password_screen_app_bar_title)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 32.dp, bottom = 18.dp, start = 6.dp, end = 6.dp),
                text = stringResource(R.string.verify_password_screen_header),
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier.padding(bottom = 9.dp, start = 14.dp, end = 14.dp),
                text = displayedMessage,
                fontSize = 12.sp,
                fontWeight = FontWeight.Thin
            )

            PasswordField(
                modifier = userAccountFieldModifier,
                value = password,
                onValueChanged = { password = it },
                placeholder = "",
                keyboardActions = KeyboardActions(
                    onDone = {
                        verifyPassword(
                            password,
                            viewModel,
                            onVerifySuccess
                        ) { errorCode ->
                            verificationErrorState =
                                verificationErrorState.copy(openDialog = true, errorCode = errorCode)
                        }
                    }
                )

            )

            Button(
                onClick = {
                    verifyPassword(
                        password,
                        viewModel,
                        onVerifySuccess
                    ) { errorCode ->
                        verificationErrorState =
                            verificationErrorState.copy(openDialog = true, errorCode = errorCode)
                    }
                },
                modifier = userAccountButtonModifier,
            ) {
                Text(
                    text = stringResource(R.string.button_next),
                    fontSize = userAccountButtonFontSize
                )
            }
        }
    }

    if (onVerifyExecuting) { OnExecutingIndicator() }

    if (verificationErrorState.openDialog && verificationErrorState.errorCode != null) {
        PasswordVerificationErrorDialog(
            errorCode = verificationErrorState.errorCode!!,
            onDismissRequest = { verificationErrorState = verificationErrorState.reset() }
        )
    }
}

fun verifyPassword(
    password: String,
    viewModel: VerifyPasswordViewModel,
    onVerifySuccess: () -> Unit,
    onVerifyFailure: (errorCode: String) -> Unit
) {
    if (password.isNotBlank()) {
        viewModel.verifyPassword(
            password = password,
            onSuccess = onVerifySuccess,
            onFailure = onVerifyFailure
        )
    }
}