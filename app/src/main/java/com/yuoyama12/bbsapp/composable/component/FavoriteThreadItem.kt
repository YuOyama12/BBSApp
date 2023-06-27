package com.yuoyama12.bbsapp.composable.component

import androidx.compose.runtime.Composable
import com.yuoyama12.bbsapp.data.Thread

@Composable
fun FavoriteThreadItem(thread: Thread) {
    ThreadItem(
        thread = thread,
        isFavorite = true,
        showFavoriteIcon = false
    )
}