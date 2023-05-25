package com.yuoyama12.bbsapp.database

import com.yuoyama12.bbsapp.data.Message
import com.yuoyama12.bbsapp.data.Thread

interface DatabaseService {
    suspend fun writeNewThread(thread: Thread)
    suspend fun writeNewMessage(message: Message)
}