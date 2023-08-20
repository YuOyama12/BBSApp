package com.yuoyama12.bbsapp.ui.thread

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yuoyama12.bbsapp.R
import com.yuoyama12.bbsapp.composable.Keyboard
import com.yuoyama12.bbsapp.composable.component.FLipHorizontalMessageItem
import com.yuoyama12.bbsapp.composable.component.MessageInputBar
import com.yuoyama12.bbsapp.composable.component.MessageItem
import com.yuoyama12.bbsapp.composable.keyboardAsState
import kotlinx.coroutines.launch

private const val DEFAULT_LIST_INDEX = -1
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ThreadScreen(
    threadId: String,
    onNavigationIconClicked: () -> Unit
) {
    val localDensity = LocalDensity.current

    val viewModel: ThreadViewModel = hiltViewModel()
    val user = viewModel.user
    val thread by viewModel.thread.collectAsState()
    val messages by viewModel.messages.collectAsState()

    val composableScope = rememberCoroutineScope()
    val keyboardState by keyboardAsState()
    val listState = rememberLazyListState()
    var visibleLastItemIndexInList by remember { mutableStateOf(DEFAULT_LIST_INDEX) }

    var columnHeightDp by remember { mutableStateOf(0.dp) }

    LaunchedEffect(Unit) {
        viewModel.initialize(threadId)
    }

    DisposableEffect(LocalLifecycleOwner.current) {
        this.onDispose {
            viewModel.onDispose(threadId)
        }
    }

    LaunchedEffect(listState.isScrollInProgress, messages.size) {
        visibleLastItemIndexInList = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: DEFAULT_LIST_INDEX
    }

    LaunchedEffect(columnHeightDp, messages.size) {
        if (visibleLastItemIndexInList == DEFAULT_LIST_INDEX) return@LaunchedEffect

        if (keyboardState == Keyboard.Opened) {
            val actualLastItemIndex = listState.layoutInfo.totalItemsCount - 1
            val isLastItemVisible = (visibleLastItemIndexInList == actualLastItemIndex)

            if (isLastItemVisible) {
                composableScope.launch {
                    listState.scrollToItem(actualLastItemIndex)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = thread.title,
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
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .consumeWindowInsets(padding)
                .imePadding()
        ) {
            LazyColumn(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        columnHeightDp = with(localDensity) { coordinates.size.height.toDp() }
                    }
                    .weight(1f),
                state = listState,
                contentPadding = padding
            ) {
                items(messages) { message ->
                    if (user.uid == message.userId) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            FLipHorizontalMessageItem(
                                userIcon = painterResource(R.drawable.ic_baseline_person_24),
                                userName = message.userName,
                                messageText = message.body
                            )
                        }
                    } else {
                        MessageItem(
                            userIcon = painterResource(R.drawable.ic_baseline_person_24),
                            userName = message.userName,
                            messageText = message.body
                        )
                    }
                }
            }

            MessageInputBar(
                modifier = Modifier.padding(all = 2.dp)
            ) { messageBody ->
                viewModel.postNewMessage(messageBody, threadId)
            }
        }
    }
}