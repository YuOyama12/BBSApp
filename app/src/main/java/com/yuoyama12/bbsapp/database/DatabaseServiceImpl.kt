package com.yuoyama12.bbsapp.database

import com.google.firebase.database.FirebaseDatabase
import com.yuoyama12.bbsapp.data.Message
import com.yuoyama12.bbsapp.data.Thread
import javax.inject.Inject

private const val THREAD = "threads"
private const val MESSAGE = "messages"
class DatabaseServiceImpl @Inject constructor(
    database: FirebaseDatabase
) : DatabaseService{
    private val reference = database.reference

    override suspend fun writeNewThread(thread: Thread) {
        val key = reference.child(THREAD).push().key ?: return
        reference.child(THREAD).child(key).setValue(thread)
    }

    override suspend fun writeNewMessage(message: Message) {
        val key = reference.child(MESSAGE).push().key ?: return
        reference.child(MESSAGE).child(key).setValue(message)
    }

}