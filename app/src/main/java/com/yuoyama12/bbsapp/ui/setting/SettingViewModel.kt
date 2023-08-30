package com.yuoyama12.bbsapp.ui.setting

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {
    fun isLoginWithEmailAndPassword(): Boolean {
        val currentUser = auth.currentUser

        return if (currentUser != null) !currentUser.isAnonymous
        else false
    }

}