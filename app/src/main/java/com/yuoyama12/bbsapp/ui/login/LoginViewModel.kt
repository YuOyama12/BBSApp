package com.yuoyama12.bbsapp.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.yuoyama12.bbsapp.data.UserAccount
import com.yuoyama12.bbsapp.firebaseerror.LoginError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

private const val TAG = "LoginViewModel"
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _onLoginExecuting = MutableStateFlow(false)
    val onLoginExecuting = _onLoginExecuting.asStateFlow()

    private fun setOnLoginExecuting(onExecuting: Boolean) {
        _onLoginExecuting.value = onExecuting
    }

    fun login(
        userAccount: UserAccount,
        onTaskCompleted: () -> Unit,
        onTaskFailed: (errorCode: String) -> Unit
    ) {
        val email = userAccount.email
        val password = userAccount.password

        setOnLoginExecuting(true)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                setOnLoginExecuting(false)

                if (task.isSuccessful) onTaskCompleted()
            }
            .addOnFailureListener { exception ->
                setOnLoginExecuting(false)

                when(exception) {
                    is FirebaseAuthException -> onTaskFailed(exception.errorCode)
                    is FirebaseTooManyRequestsException -> onTaskFailed(LoginError.TooManyRequests.code)
                    else -> Log.w(TAG, exception)
                }
            }
    }

    fun loginAsAnonymous(
        onTaskCompleted: () -> Unit,
        onTaskFailed: (errorCode: String) -> Unit
    ) {
        setOnLoginExecuting(true)
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                setOnLoginExecuting(false)

                if (task.isSuccessful) onTaskCompleted()
            }
            .addOnFailureListener { exception ->
                setOnLoginExecuting(false)

                when(exception) {
                    is FirebaseAuthException -> onTaskFailed(exception.errorCode)
                    else -> Log.w(TAG, exception)
                }
            }
    }
}