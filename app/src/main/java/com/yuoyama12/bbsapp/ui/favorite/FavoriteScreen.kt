package com.yuoyama12.bbsapp.ui.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yuoyama12.bbsapp.composable.component.FavoriteThreadItem
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(
    onItemClicked: (threadId: String) -> Unit
) {
    val viewModel: FavoriteViewModel = hiltViewModel()
    val favoriteThreads = viewModel.threads.collectAsState()

    val composableScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        composableScope.launch { viewModel.loadFavoriteThreads() }
    }

    DisposableEffect(Unit) {
        onDispose { viewModel.resetFavoriteThreads() }
    }

    LazyColumn {
        items(items = favoriteThreads.value) { thread ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClicked(thread.threadId) }
            ) {
                FavoriteThreadItem(thread = thread)
                Divider(thickness = 0.1.dp)
            }
        }
    }
}