@file:OptIn(ExperimentalMaterial3Api::class)

package com.yuoyama12.bbsapp.composable

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun NormalTopAppBar(
    text: String
) {
    TopAppBar(
        title = {
            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
fun NormalActionTopAppBar(
    text: String,
    action: () -> Unit,
    actionIcon: ImageVector,
    popupMenu: @Composable () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(onClick = { action() }) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = null
                )

                popupMenu()
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}