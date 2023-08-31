package com.yuoyama12.bbsapp.ui.verifypassword

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.yuoyama12.bbsapp.firebaseerror.LoginError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

private const val TAG = "VerifyPasswordViewModel"
@HiltViewModel
class VerifyPasswordViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _onVerifyExecuting = MutableStateFlow(false)
    val onVerifyExecuting = _onVerifyExecuting.asStateFlow()

    private fun setOnVerifyExecuting(onExecuting: Boolean) {
        _onVerifyExecuting.value = onExecuting
    }

    fun verifyPassword(
        password: String,
        onSuccess: () -> Unit,
        onFailure: (errorCode: String) -> Unit
    ) {
        setOnVerifyExecuting(true)
        val credential = EmailAuthProvider.getCredential(auth.currentUser!!.email!!, password)

        auth.currentUser!!.reauthenticate(credential)
            .addOnCompleteListener { task ->
                setOnVerifyExecuting(false)

                if (task.isSuccessful) onSuccess()
            }
            .addOnFailureListener { exception ->
                setOnVerifyExecuting(false)

                when(exception) {
                    is FirebaseAuthException -> onFailure(exception.errorCode)
                    is FirebaseTooManyRequestsException -> onFailure(LoginError.TooManyRequests.code)
                    else -> Log.w(TAG, exception)
                }
            }
    }

}