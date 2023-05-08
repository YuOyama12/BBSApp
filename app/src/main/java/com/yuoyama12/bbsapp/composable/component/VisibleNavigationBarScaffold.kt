package com.yuoyama12.bbsapp.composable.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisibleNavigationBarScaffold(
    modifier: Modifier = Modifier,
    showNavigationBar: Boolean,
    topBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    if (showNavigationBar) {
        Scaffold(
            modifier = modifier,
            topBar = topBar,
            content = content
        )
    } else {
        Scaffold (
            content = content
        )
    }
}