package com.yuoyama12.bbsapp.ui.threadslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yuoyama12.bbsapp.R
import com.yuoyama12.bbsapp.composable.SimpleInputDialog
import com.yuoyama12.bbsapp.composable.component.ThreadItem

@Composable
fun ThreadsListScreen(
    onItemClicked: (threadId: String) -> Unit
) {
    val viewModel: ThreadsListViewModel = hiltViewModel()
    val threads by viewModel.threads.collectAsState()
    val favorites = viewModel.favorites.collectAsState()

    var openCreateNewThreadDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadFavoritesList()
    }

    DisposableEffect(LocalLifecycleOwner.current) {
        this.onDispose {
            viewModel.loadFavoritesList()
        }
    }

    Column {
        Box(modifier = Modifier.shadow(elevation = 12.dp)) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                onClick = { openCreateNewThreadDialog = true },
                shape = RectangleShape
            ) {
                Icon(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )

                Text(text = stringResource(R.string.create_new_thread_button))
            }
        }

        LazyColumn {
            items(
                items = threads,
                key = { it.threadId }
            ) { thread ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClicked(thread.threadId) }
                ) {
                    ThreadItem(
                        thread = thread,
                        isFavorite = favorites.value.contains(thread.threadId)
                    ) { threadId, favorite ->
                        if (favorite) { viewModel.addThreadIdToFavorites(threadId) }
                        else { viewModel.removeThreadIdFromFavorites(threadId) }
                    }
                    HorizontalDivider()
                }
            }
        }
    }

    if (openCreateNewThreadDialog) {
        SimpleInputDialog(
            title = stringResource(R.string.dialog_create_new_thread_title),
            textFieldPlaceholder = stringResource(R.string.dialog_create_new_thread_text_placeholder),
            positiveButtonText = stringResource(R.string.dialog_create_new_thread_text_positive),
            onDismissRequest = { openCreateNewThreadDialog = false },
            onPositiveButtonClicked = { title ->
                val thread = viewModel.getThreadWithUserId(title)
                viewModel.writeNewThread(thread)
            }
        )
    }

}