package com.yuoyama12.bbsapp.ui.threadslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ThreadsList(
    onItemClicked: () -> Unit
) {
    val list = listOf("test1", "test2", "test3", "test4")
    
    LazyColumn {
        items(items = list) { item ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClicked() }
            ) {
                Text(
                    text = item,
                    modifier = Modifier.padding(16.dp)
                )
            }

        }
    }

}