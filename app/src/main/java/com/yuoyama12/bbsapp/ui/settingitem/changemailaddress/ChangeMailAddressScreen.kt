package com.yuoyama12.bbsapp.ui.settingitem.changemailaddress

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yuoyama12.bbsapp.R
import com.yuoyama12.bbsapp.UserAccountInfoValidation
import com.yuoyama12.bbsapp.composable.*
import com.yuoyama12.bbsapp.composable.component.OnExecutingIndicator
import com.yuoyama12.bbsapp.data.FirebaseErrorState

private const val TAG = "ChangeMailAddressScreen"
@Composable
fun ChangeMailAddressScreen(moveToMainScreen: () -> Unit) {
    val context = LocalContext.current
    val viewModel: ChangeMailAddressViewModel = hiltViewModel()
    val onUpdateExecuting by viewModel.onUpdateExecuting.collectAsState()

    var newEmail by rememberSaveable { mutableStateOf("") }
    val changeCompleteMessage = stringResource(R.string.change_mail_address_complete_message)
    var errorState by rememberSaveable { mutableStateOf(FirebaseErrorState()) }

    Column {
        NormalTopAppBar(
            text = stringResource(R.string.setting_title_about_changing_mail_address)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 32.dp, bottom = 18.dp, start = 7.dp, end = 7.dp),
                text = stringResource(R.string.change_mail_address_screen_header),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier
                    .padding(bottom = 9.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(R.string.change_mail_address_screen_mail_header, viewModel.emailAddress),
                fontSize = 16.sp
            )

            Text(
                modifier = Modifier.padding(bottom = 12.dp, start = 16.dp, end = 16.dp),
                text = stringResource(R.string.change_mail_address_screen_caution_message),
                color = Color.Red,
                fontSize = 14.sp

            )

            EmailField(
                modifier = userAccountFieldModifier,
                value = newEmail,
                onValueChanged = { newEmail = it },
            )

            Button(
                onClick = {
                    if (UserAccountInfoValidation(context).isInputtedEmailValid(newEmail)) {
                        viewModel.updateEmail(
                            email = newEmail,
                            onSuccess = {
                                Toast.makeText(
                                    context,
                                    changeCompleteMessage,
                                    Toast.LENGTH_SHORT
                                ).show()

                                moveToMainScreen()
                            },
                            onFailure = { errorCode ->
                                Log.d(TAG, errorCode)

                                errorState =
                                    errorState.copy(openDialog = true, errorCode = errorCode)
                            }
                        )
                    }
                },
                modifier = userAccountButtonModifier,
            ) {
                Text(
                    text = stringResource(R.string.change_mail_address_screen_submit_button),
                    fontSize = userAccountButtonFontSize
                )
            }
        }
    }

    if (onUpdateExecuting) { OnExecutingIndicator() }

    if (errorState.openDialog && errorState.errorCode != null) {
        MiscellaneousErrorDialog(
            errorCode = errorState.errorCode!!,
            onDismissRequest = { errorState = errorState.reset() }
        )
    }

}