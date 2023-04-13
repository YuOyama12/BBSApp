package com.yuoyama12.bbsapp.component

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
    var message by remember { mutableStateOf("") }

    Row(modifier = modifier.height(intrinsicSize = IntrinsicSize.Min)) {
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            modifier = Modifier
                .heightIn(max = 120.dp)
                .weight(0.85f)
        )

        Column(
            modifier = Modifier
                .weight(0.15f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = {
                    if (message.trim().isNotEmpty()) {
                        onSubmitClicked(message)
                    }
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