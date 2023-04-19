package com.yuoyama12.bbsapp.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisibleTopBarScaffold(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    topBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    if (isVisible) {
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