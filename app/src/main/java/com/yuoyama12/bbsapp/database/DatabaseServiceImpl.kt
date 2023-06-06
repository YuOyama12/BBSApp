package com.yuoyama12.bbsapp.database

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.yuoyama12.bbsapp.data.Message
import com.yuoyama12.bbsapp.data.Thread
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

private const val THREAD = "threads"
private const val MESSAGE = "messages"
@Singleton
class DatabaseServiceImpl @Inject constructor(
    database: FirebaseDatabase
) : DatabaseService{
    override val threads = MutableStateFlow(SnapshotStateList<Thread>())
    override val messages = MutableStateFlow(SnapshotStateList<Message>())

    private val threadRef = database.reference.child(THREAD)
    private val messageRef = database.reference.child(MESSAGE)

    private val eventListenerForMessageRef =
        object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messages.value.clear()

                for (child in snapshot.children) {
                    val dataSnapshot = child.getValue(Message::class.java)
                    if (dataSnapshot != null) {
                        messages.value.add(dataSnapshot)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        }

    init {
        threadRef.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    threads.value.clear()

                    for (child in snapshot.children) {
                        val dataSnapshot = child.getValue(Thread::class.java)
                        if (dataSnapshot != null) {
                            threads.value.add(dataSnapshot)
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }
            }
        )
    }

    private fun getCurrentTime(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")
        return current.format(formatter)
    }

    override fun setListenerOnMessageRef(threadId: String) {
        messageRef.child(threadId).addValueEventListener(eventListenerForMessageRef)
    }

    override fun removeListenerOnMessageRef(threadId: String) {
        messageRef.child(threadId).removeEventListener(eventListenerForMessageRef)
    }

    override suspend fun writeNewThread(thread: Thread) {
        val key = threadRef.push().key ?: return
        val currentTime = getCurrentTime()
        val threadWithIdAndTime =
            thread.copy(
                threadId = key,
                createdDate = currentTime,
                modifiedDate = currentTime,
            )

        threadRef.child(key).setValue(threadWithIdAndTime)
    }

    override suspend fun writeNewMessage(message: Message) {
        val key = messageRef.push().key ?: return
        val currentTime = getCurrentTime()
        val messageWithTime =
            message.copy(postedDate = currentTime)

        updateThread(messageWithTime.body, messageWithTime.threadId)
        messageRef.child(message.threadId).child(key).setValue(messageWithTime)
    }

    private fun updateThread(messageBody: String, threadId: String) {
        val threadUpdate = mutableMapOf<String, Any>()
        threadUpdate["latestMessageBody"] = messageBody
        threadUpdate["modifiedDate"] = getCurrentTime()

        threadRef.child(threadId).updateChildren(threadUpdate)
    }

    override suspend fun getThreadFrom(threadId: String): Thread? =
        threadRef.child(threadId).get().await().getValue(Thread::class.java)
}