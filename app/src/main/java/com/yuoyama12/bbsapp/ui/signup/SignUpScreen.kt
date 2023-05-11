package com.yuoyama12.bbsapp.ui.signup

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.yuoyama12.bbsapp.R
import com.yuoyama12.bbsapp.composable.*
import com.yuoyama12.bbsapp.ui.theme.BBSAppTheme

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier
) {
    NormalTopAppBar(
        text = stringResource(R.string.sign_up_screen_app_bar_title)
    )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }
        var repeatPassword by rememberSaveable { mutableStateOf("") }
        var userName by rememberSaveable { mutableStateOf("") }

        EmailField(
            modifier = userAccountFieldModifier,
            value = email,
            onValueChanged = { email = it }
        )

        PasswordField(
            modifier = userAccountFieldModifier,
            value = password,
            onValueChanged = { password = it },
            placeholder = stringResource(R.string.password_placeholder)
        )

        PasswordField(
            modifier = userAccountFieldModifier,
            value = repeatPassword,
            onValueChanged = { repeatPassword = it },
            placeholder = stringResource(R.string.repeat_password_placeholder)
        )

        UserNameField(
            modifier = userAccountFieldModifier,
            value = userName,
            onValueChanged = { userName = it }
        )

        Button(
            onClick = {  },
            modifier = userAccountButtonModifier,
        ) {
            Text(
                text = stringResource(R.string.sign_up_button),
                fontSize = userAccountButtonFontSize
            )
        }
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
            SignUpScreen()
        }
    }
}