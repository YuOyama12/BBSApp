package com.yuoyama12.bbsapp.ui.threadslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.yuoyama12.bbsapp.data.Thread
import com.yuoyama12.bbsapp.database.DatabaseService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThreadsListViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: DatabaseService
) : ViewModel() {
    val threads = database.threads.asStateFlow()

    fun getThreadWithUserId(title: String): Thread {
        return Thread(
            userId = auth.currentUser!!.uid,
            title = title
        )
    }

    fun writeNewThread(thread: Thread) {
        viewModelScope.launch {
            database.writeNewThread(thread)
        }
    }
}