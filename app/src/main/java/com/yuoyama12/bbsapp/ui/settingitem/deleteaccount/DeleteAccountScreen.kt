package com.yuoyama12.bbsapp.ui.settingitem.deleteaccount

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yuoyama12.bbsapp.R
import com.yuoyama12.bbsapp.composable.*
import com.yuoyama12.bbsapp.composable.component.OnExecutingIndicator
import com.yuoyama12.bbsapp.data.FirebaseErrorState

private const val TAG = "DeleteAccountScreen"
private val horizontalDividerPadding = 14.dp
@Composable
fun DeleteAccountScreen(
    moveToLoginScreen: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: DeleteAccountViewModel = hiltViewModel()
    val cautionStringArray = stringArrayResource(R.array.delete_account_caution_messages)
    var agreed by rememberSaveable { mutableStateOf(false) }

    val onDeleteExecuting by viewModel.onDeleteExecuting.collectAsState()

    val deleteCompleteMessage = stringResource(R.string.delete_complete_message)
    var errorState by rememberSaveable { mutableStateOf(FirebaseErrorState()) }

    Column {
        NormalTopAppBar(
            text = stringResource(R.string.setting_title_about_deleting_the_account)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 32.dp, bottom = 12.dp, start = 9.dp, end = 9.dp),
                text = stringResource(R.string.delete_account_screen_header),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontalDividerPadding)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                Icon(
                    modifier = Modifier.padding(end = 12.dp),
                    imageVector = Icons.Default.Warning,
                    contentDescription = null
                )

                Text(
                    text = stringResource(R.string.delete_account_caution_header),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            cautionStringArray.forEach { cautionString ->
                Text(
                    modifier = Modifier.padding(top = 8.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
                    text = cautionString,
                    fontSize = 14.sp,
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(horizontalDividerPadding)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    checked = agreed,
                    onCheckedChange = { agreed = !agreed }
                )

                Text(
                    text = stringResource(R.string.delete_account_agreement_with_cautions_message),
                    fontSize = 14.sp
                )
            }

            Button(
                enabled = agreed,
                onClick = {
                    viewModel.deleteAccount(
                        onSuccess = {
                            Toast.makeText(
                                context,
                                deleteCompleteMessage,
                                Toast.LENGTH_SHORT
                            ).show()

                            moveToLoginScreen()
                        },
                        onFailure = { errorCode ->
                            Log.d(TAG, errorCode)

                            errorState =
                                errorState.copy(openDialog = true, errorCode = errorCode)
                        }
                    )
                },
                modifier = userAccountButtonModifier,
            ) {
                Text(
                    text = stringResource(R.string.delete_account_screen_submit_button),
                    fontSize = userAccountButtonFontSize
                )
            }
        }
    }

    if (onDeleteExecuting) { OnExecutingIndicator() }

    if (errorState.openDialog && errorState.errorCode != null) {
        MiscellaneousErrorDialog(
            errorCode = errorState.errorCode!!,
            onDismissRequest = { errorState = errorState.reset() }
        )
    }
}