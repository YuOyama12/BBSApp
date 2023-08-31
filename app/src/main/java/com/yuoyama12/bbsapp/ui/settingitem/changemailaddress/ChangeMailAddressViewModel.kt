package com.yuoyama12.bbsapp.ui.settingitem.changemailaddress

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

private const val TAG = "ChangeMailAddressViewModel"
@HiltViewModel
class ChangeMailAddressViewModel @Inject constructor(
    auth: FirebaseAuth
) : ViewModel() {
    private val _onUpdateExecuting = MutableStateFlow(false)
    val onUpdateExecuting = _onUpdateExecuting.asStateFlow()

    private val currentUser = auth.currentUser!!
    val emailAddress = currentUser.email!!

    private fun setOnUpdateExecuting(onExecuting: Boolean) {
        _onUpdateExecuting.value = onExecuting
    }

    fun updateEmail(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (errorCode: String) -> Unit
    ) {
        setOnUpdateExecuting(true)

        currentUser.updateEmail(email)
            .addOnCompleteListener { task ->
                setOnUpdateExecuting(false)

                if (task.isSuccessful) {
                    currentUser.sendEmailVerification()
                    onSuccess()
                }
            }
            .addOnFailureListener { exception ->
                setOnUpdateExecuting(false)

                when(exception) {
                    is FirebaseAuthException -> onFailure(exception.errorCode)
                    is FirebaseTooManyRequestsException -> onFailure(LoginError.TooManyRequests.code)
                    else -> Log.w(TAG, exception)
                }
            }
    }
}