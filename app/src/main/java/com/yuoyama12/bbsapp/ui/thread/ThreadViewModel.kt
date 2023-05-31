package com.yuoyama12.bbsapp.ui.thread

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuoyama12.bbsapp.DEFAULT_THREAD_ID
import com.yuoyama12.bbsapp.database.DatabaseService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.yuoyama12.bbsapp.data.Thread
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class ThreadViewModel @Inject constructor(
    private val database: DatabaseService
) : ViewModel() {
    private val _thread = MutableStateFlow(Thread())
    val thread = _thread.asStateFlow()

    fun initialize(threadId: String) {
        if (threadId != DEFAULT_THREAD_ID) {
            viewModelScope.launch {
                _thread.value = database.getThreadFrom(threadId) ?: Thread()
            }
        }
    }

}