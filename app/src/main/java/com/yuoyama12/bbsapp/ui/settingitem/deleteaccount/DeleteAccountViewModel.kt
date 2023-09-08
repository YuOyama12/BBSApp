package com.yuoyama12.bbsapp.ui.settingitem.deleteaccount

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.yuoyama12.bbsapp.firebaseerror.LoginError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

private const val TAG = "DeleteAccountViewModel"
@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    auth: FirebaseAuth
) : ViewModel() {
    private val _onDeleteExecuting = MutableStateFlow(false)
    val onDeleteExecuting = _onDeleteExecuting.asStateFlow()

    private val currentUser = auth.currentUser!!

    private fun setOnDeleteExecuting(onExecuting: Boolean) {
        _onDeleteExecuting.value = onExecuting
    }

    fun deleteAccount(
        onSuccess: () -> Unit,
        onFailure: (errorCode: String) -> Unit
    ) {
        setOnDeleteExecuting(true)

        currentUser.delete()
            .addOnCompleteListener { task ->
                setOnDeleteExecuting(false)

                if (task.isSuccessful) {
                    currentUser.sendEmailVerification()
                    onSuccess()
                }
            }
            .addOnFailureListener { exception ->
                setOnDeleteExecuting(false)

                when(exception) {
                    is FirebaseAuthException -> onFailure(exception.errorCode)
                    is FirebaseTooManyRequestsException -> onFailure(LoginError.TooManyRequests.code)
                    else -> Log.w(TAG, exception)
                }
            }
    }
}