package com.yuoyama12.bbsapp.ui.login

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    fun isNotLogin(auth: FirebaseAuth): Boolean {
        return auth.currentUser == null
    }

    fun loginAsGuest(auth: FirebaseAuth) {
        auth.signInAnonymously()
    }
}