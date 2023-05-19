package com.yuoyama12.bbsapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.yuoyama12.bbsapp.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val auth: FirebaseAuth
): ViewModel() {
    val isFirstBoot = runBlocking { dataStoreManager.getIsFirstBoot() }
    val isLoginAsAnonymous = auth.currentUser!!.isAnonymous

    fun setIsFirstBoot(isFirstBoot: Boolean) {
        viewModelScope.launch {
            dataStoreManager.setIsFirstBoot(isFirstBoot)
        }
    }

    fun logout() {
        auth.signOut()
    }

}