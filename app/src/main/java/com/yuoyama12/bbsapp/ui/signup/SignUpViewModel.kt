package com.yuoyama12.bbsapp.ui.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.yuoyama12.bbsapp.data.UserAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

private const val TAG = "SignUpViewModel"
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _onSignUpExecuting = MutableStateFlow(false)
    val onSignUpExecuting = _onSignUpExecuting.asStateFlow()

    private fun setOnSignUpExecuting(onExecuting: Boolean) {
        _onSignUpExecuting.value = onExecuting
    }

    fun createNewAccount(
        userAccount: UserAccount,
        onTaskCompleted: () -> Unit,
        onTaskFailed: (errorCode: String) -> Unit
    ) {
        if (auth.currentUser != null) {
            if (auth.currentUser!!.isAnonymous) {
                linkAccount(userAccount, onTaskCompleted, onTaskFailed)
                return
            }
        }

        val email = userAccount.email
        val password = userAccount.password
        val userName = userAccount.userName

        setOnSignUpExecuting(true)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { firstTask ->

                if (firstTask.isSuccessful) {
                    auth.signInWithEmailAndPassword(email, password)
                    val userProfile = userProfileChangeRequest { displayName = userName }

                    val user = auth.currentUser!!
                    user.updateProfile(userProfile)
                        .addOnCompleteListener { secondTask ->
                            if (secondTask.isSuccessful) {
                                setOnSignUpExecuting(false)
                                onTaskCompleted()
                            }
                        }
                        .addOnFailureListener { exception ->
                            setOnSignUpExecuting(false)

                            when (exception) {
                                is FirebaseAuthException -> onTaskFailed(exception.errorCode)
                                else -> Log.w(TAG, exception)
                            }
                        }
                }
            }
            .addOnFailureListener { exception ->
                setOnSignUpExecuting(false)

                when (exception) {
                    is FirebaseAuthException -> onTaskFailed(exception.errorCode)
                    else -> Log.w(TAG, exception)
                }
            }
    }

    private fun linkAccount(
        userAccount: UserAccount,
        onTaskCompleted: () -> Unit,
        onTaskFailed: (errorCode: String) -> Unit
    ) {
        val credential = EmailAuthProvider.getCredential(userAccount.email, userAccount.password)
        val userName = userAccount.userName

        setOnSignUpExecuting(true)
        auth.currentUser!!.linkWithCredential(credential)
            .addOnCompleteListener { firstTask ->
                if (firstTask.isSuccessful) {
                    val userProfile = userProfileChangeRequest { displayName = userName }

                    val user = auth.currentUser!!
                    user.updateProfile(userProfile)
                        .addOnCompleteListener { secondTask ->
                            if (secondTask.isSuccessful) {
                                setOnSignUpExecuting(false)
                                onTaskCompleted()
                            }
                        }
                        .addOnFailureListener { exception ->
                            setOnSignUpExecuting(false)

                            when (exception) {
                                is FirebaseAuthException -> onTaskFailed(exception.errorCode)
                                else -> Log.w(TAG, exception)
                            }
                        }
                }
            }
            .addOnFailureListener { exception ->
                setOnSignUpExecuting(false)

                when (exception) {
                    is FirebaseAuthException -> onTaskFailed(exception.errorCode)
                    else -> Log.w(TAG, exception)
                }
            }
    }

}