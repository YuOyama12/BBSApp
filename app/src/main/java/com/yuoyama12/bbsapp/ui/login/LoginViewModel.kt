package com.yuoyama12.bbsapp.ui.login

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.yuoyama12.bbsapp.data.UserAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {
    fun isNotLogin(): Boolean {
        return auth.currentUser == null
    }

    fun login(
        userAccount: UserAccount,
        onTaskCompleted: () -> Unit,
        onTaskFailed: () -> Unit
    ) {
        val email = userAccount.email
        val password = userAccount.password

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) { onTaskCompleted() }
                else { onTaskFailed() }
            }
    }

    fun loginAsAnonymous(
        onTaskCompleted: () -> Unit,
        onTaskFailed: () -> Unit
    ) {
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) { onTaskCompleted() }
                else { onTaskFailed() }
            }
    }
}