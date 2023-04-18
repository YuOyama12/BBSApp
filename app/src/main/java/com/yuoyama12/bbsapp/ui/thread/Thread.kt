package com.yuoyama12.bbsapp.ui.thread

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.yuoyama12.bbsapp.component.MessageInputBar
import androidx.compose.material3.Icon as Icon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Thread(
    threadTitle: String,
    onNavigationIconClicked: () -> Unit
) {

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = threadTitle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigationIconClicked() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            MessageInputBar{  }
        }
    }
}