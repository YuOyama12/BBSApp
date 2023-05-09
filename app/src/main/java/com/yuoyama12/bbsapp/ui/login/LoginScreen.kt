package com.yuoyama12.bbsapp.ui.login

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yuoyama12.bbsapp.R
import com.yuoyama12.bbsapp.composable.EmailField
import com.yuoyama12.bbsapp.composable.NormalTopAppBar
import com.yuoyama12.bbsapp.composable.PasswordField
import com.yuoyama12.bbsapp.ui.theme.BBSAppTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {
    NormalTopAppBar(
        text = stringResource(R.string.login_screen_app_bar_title)
    )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val fieldModifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        val buttonModifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        val buttonTextSize = 16.sp

        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }

        EmailField(
            modifier = fieldModifier,
            value = email,
            onValueChanged = { email = it }
        )

        PasswordField(
            modifier = fieldModifier,
            value = password,
            onValueChanged = { password = it }
        )

        Button(
            onClick = {  },
            modifier = buttonModifier,
        ) {
            Text(
                text = stringResource(R.string.login_button),
                fontSize = buttonTextSize
            )
        }

        Button(
            onClick = {  },
            modifier = buttonModifier,
        ) {
            Text(
                text = stringResource(R.string.sign_up_button),
                fontSize = buttonTextSize
            )
        }


        TextButton(
            onClick = {  },
            modifier = buttonModifier,
        ) {
            Text(
                text = stringResource(R.string.login_as_guest_button),
                fontSize = buttonTextSize
            )
        }
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
            LoginScreen()
        }
    }
}
