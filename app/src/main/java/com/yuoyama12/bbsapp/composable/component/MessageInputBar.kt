package com.yuoyama12.bbsapp.composable.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MessageInputBar(
    modifier: Modifier = Modifier,
    onSubmitClicked: (String) -> Unit
) {
    var messageBody by remember { mutableStateOf("") }

    Row(modifier = modifier.height(intrinsicSize = IntrinsicSize.Min)) {
        OutlinedTextField(
            value = messageBody,
            onValueChange = { messageBody = it },
            modifier = Modifier
                .heightIn(max = 120.dp)
                .weight(0.85f)
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.15f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = {
                    if (messageBody.trim().isNotEmpty()) {
                        onSubmitClicked(messageBody)
                    }
                    messageBody = ""
                },
                modifier = Modifier.padding(vertical = 2.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null
                )
            }

        }
    }
}