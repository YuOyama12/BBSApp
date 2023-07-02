package com.yuoyama12.bbsapp.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.yuoyama12.bbsapp.R

private val spacer = Modifier.padding(vertical = 3.dp)
private val messagePadding = Modifier.padding(bottom = 28.dp)

@Composable
fun SimpleInputDialog(
    title: String,
    message: String = "",
    preInputtedText:String = "",
    textFieldPlaceholder: String,
    positiveButtonText: String,
    onDismissRequest: () -> Unit,
    onPositiveButtonClicked: (String) -> Unit
) {
    var inputtedText by rememberSaveable { mutableStateOf(preInputtedText) }

    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface
        ) {
            Box (
                modifier = Modifier.padding(horizontal = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Spacer(Modifier.padding(vertical = 8.dp))

                    Text(
                        text = title,
                        modifier = Modifier.alpha(DefaultAlpha),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(spacer)

                    if (message != "") {
                        Text(
                            text = message,
                            modifier = messagePadding.alpha(DefaultAlpha),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(spacer)
                    }

                    OutlinedTextField(
                        value = inputtedText,
                        onValueChange = { inputtedText = it },
                        placeholder = { Text(textFieldPlaceholder) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (inputtedText.trim() != preInputtedText) {
                                    onPositiveButtonClicked(inputtedText)
                                }
                                onDismissRequest()
                            }
                        )
                    )

                    Spacer(spacer)

                    Row(
                        modifier = Modifier.align(Alignment.End),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TextButton(onClick = { onDismissRequest() }) {
                            Text(stringResource(R.string.dialog_cancel))
                        }
                        TextButton(
                            onClick = {
                                if (inputtedText.trim() != preInputtedText) {
                                    onPositiveButtonClicked(inputtedText)
                                }
                                onDismissRequest()
                            }
                        ) {
                            Text(positiveButtonText)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ConfirmationDialog(
    title: String,
    message: String = "",
    positiveButtonText: String,
    onDismissRequest: () -> Unit,
    onPositiveClicked: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton = {
            TextButton(
                onClick = {
                    onPositiveClicked()
                    onDismissRequest()
                }
            ) {
                Text(text = positiveButtonText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest() }
            ) {
                Text(text = stringResource(R.string.dialog_cancel))
            }
        }

    )
}