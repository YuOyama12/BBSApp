package com.yuoyama12.bbsapp.database

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.yuoyama12.bbsapp.data.Message
import com.yuoyama12.bbsapp.data.Thread
import kotlinx.coroutines.flow.MutableStateFlow

interface DatabaseService {
    val threads: MutableStateFlow<SnapshotStateList<Thread>>
    val messages: MutableStateFlow<SnapshotStateList<Message>>

    fun setListenerOnMessageRef(threadId: String)
    fun removeListenerOnMessageRef(threadId: String)
    suspend fun writeNewThread(thread: Thread)
    suspend fun writeNewMessage(message: Message)
    suspend fun getThreadFrom(threadId: String): Thread?
}