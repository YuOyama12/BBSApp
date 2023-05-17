package com.yuoyama12.bbsapp.ui.login

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.yuoyama12.bbsapp.data.UserAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _onLoginExecuting = MutableStateFlow(false)
    val onLoginExecuting = _onLoginExecuting.asStateFlow()

    private fun setOnLoginExecuting(onExecuting: Boolean) {
        _onLoginExecuting.value = onExecuting
    }

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

        setOnLoginExecuting(true)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    setOnLoginExecuting(false)
                    onTaskCompleted()
                }
                else {
                    setOnLoginExecuting(false)
                    onTaskFailed()
                }
            }
    }

    fun loginAsAnonymous(
        onTaskCompleted: () -> Unit,
        onTaskFailed: () -> Unit
    ) {
        setOnLoginExecuting(true)
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onTaskCompleted()
                    setOnLoginExecuting(false)
                }
                else {
                    onTaskFailed()
                    setOnLoginExecuting(false)
                }
            }
    }
}