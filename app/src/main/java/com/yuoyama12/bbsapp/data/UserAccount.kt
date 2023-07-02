package com.yuoyama12.bbsapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserAccount(
    val email: String = "",
    val password: String = "",
    val repeatedPassword: String = "",
    val userName: String = ""
) : Parcelable {
    fun isNotEmpty(): Boolean {
        return this.email.isNotEmpty() ||
                this.userName.isNotEmpty() ||
                this.password.isNotEmpty() ||
                this.repeatedPassword.isNotEmpty()
    }
}
