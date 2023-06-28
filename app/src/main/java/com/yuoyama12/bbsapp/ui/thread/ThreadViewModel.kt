package com.yuoyama12.bbsapp.ui.thread

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.yuoyama12.bbsapp.DEFAULT_THREAD_ID
import com.yuoyama12.bbsapp.data.Message
import com.yuoyama12.bbsapp.database.DatabaseService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.yuoyama12.bbsapp.data.Thread
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class ThreadViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: DatabaseService
) : ViewModel() {
    private val _thread = MutableStateFlow(Thread())
    val thread = _thread.asStateFlow()

    val messages = database.messages.asStateFlow()

    val user = auth.currentUser!!

    fun initialize(threadId: String) {
        if (threadId != DEFAULT_THREAD_ID) {
            viewModelScope.launch {
                _thread.value = database.getThreadFrom(threadId) ?: Thread()
                database.setListenerOnMessageRef(threadId)
            }
        }
    }

    fun postNewMessage(messageBody: String, threadId: String) {
        val userName =
            if (auth.currentUser!!.isAnonymous) {
                "anonymous(${auth.currentUser!!.uid.substring(range = 0..6)})"
            } else {
                auth.currentUser!!.displayName
            }

        val message =
            Message(
                threadId = threadId,
                userId = auth.currentUser!!.uid,
                userName = userName!!,
                body = messageBody
            )

        viewModelScope.launch {
            database.writeNewMessage(message)
        }
    }

    fun onDispose(threadId: String) {
        viewModelScope.launch {
            database.removeListenerOnMessageRef(threadId)
            _thread.value = Thread()
            database.messages.value.clear()
        }
    }

}