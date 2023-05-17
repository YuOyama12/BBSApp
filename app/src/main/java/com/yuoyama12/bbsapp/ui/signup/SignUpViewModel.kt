package com.yuoyama12.bbsapp.ui.signup

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.yuoyama12.bbsapp.data.UserAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

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
        onTaskFailed: () -> Unit
    ) {
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
                            else {
                                setOnSignUpExecuting(false)
                                onTaskFailed()
                            }
                        }
                } else {
                    setOnSignUpExecuting(false)
                    onTaskFailed()
                }
            }
    }

}