package com.yuoyama12.bbsapp.ui.favorite

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.yuoyama12.bbsapp.data.Thread
import com.yuoyama12.bbsapp.database.DatabaseService
import com.yuoyama12.bbsapp.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val dataStore: DataStoreManager,
    private val database: DatabaseService
) : ViewModel() {
    private val _threads = MutableStateFlow(SnapshotStateList<Thread>())
    val threads = _threads.asStateFlow()

    suspend fun loadFavoriteThreads() {
        val favoriteThreadIds = dataStore.getFavorites()

        favoriteThreadIds.forEach { id ->
            val thread = database.getThreadFrom(id) ?: Thread()
            _threads.value.add(thread)
        }
    }

    fun resetFavoriteThreads() {
        _threads.value.clear()
    }

}