package com.yuoyama12.bbsapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FirebaseErrorState(
    var openDialog: Boolean = false,
    var errorCode: String? = null
) : Parcelable {
    fun reset() = FirebaseErrorState(false, null)
}
