package com.yuoyama12.bbsapp

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.StringRes
import com.yuoyama12.bbsapp.data.UserAccount

private const val MIN_PASS_LENGTH = 6
class UserAccountInfoValidation(private val context: Context) {
    fun isInputtedInfoValidForSignUp(userAccount: UserAccount): Boolean {
        val email = userAccount.email
        val password = userAccount.password
        val repeatedPassword = userAccount.repeatedPassword
        val userName = userAccount.userName

        if (email.isBlank()
            && password.isBlank()
            && repeatedPassword.isBlank()
            && userName.isBlank()
        ) {
            return false
        }

        if (!isValidEmail(email)) {
            showMessage(R.string.error_message_email)
            return false
        }
        if (!isValidPassword(password)) {
            showMessage(R.string.error_message_password)
            return false
        }
        if (!arePasswordsSame(password, repeatedPassword)) {
            showMessage(R.string.error_message_repeated_password)
            return false
        }
        if (!isValidUserName(userName)) {
            showMessage(R.string.error_message_user_name)
            return false
        }

        return true
    }

    fun isInputtedInfoValidForLogin(userAccount: UserAccount): Boolean {
        val email = userAccount.email
        val password = userAccount.password

        if (email.isBlank() && password.isBlank()
            || password.isBlank()
        ) {
            return false
        }

        if (!isValidEmail(email)) {
            showMessage(R.string.error_message_email)
            return false
        }

        return true
    }

    private fun showMessage(@StringRes message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun isValidEmail(email: String): Boolean {
        return email.trim().isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.trim().isNotBlank() && password.length >= MIN_PASS_LENGTH
    }

    private fun arePasswordsSame(firstPassword: String, secondPassword: String): Boolean {
        return firstPassword == secondPassword
    }

    private fun isValidUserName(userName: String): Boolean {
        return userName.trim().isNotBlank()
    }
}