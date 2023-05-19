package com.yuoyama12.bbsapp.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <E : Enum<*>> SimplePopupMenu(
    modifier: Modifier = Modifier,
    menuItemContent: List<E>,
    menuItemTitles: List<String>,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onMenuItemClicked: (clickedItem: E) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDismissRequest() }
        ) {
            menuItemTitles.forEachIndexed { index, title ->
                DropdownMenuItem(
                    text = { Text(text = title) },
                    onClick = {
                        onMenuItemClicked(menuItemContent[index])
                        onDismissRequest()
                    }
                )

                MenuDefaults.Divider()
            }
        }
    }

}