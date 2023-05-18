package com.yuoyama12.bbsapp.ui.actions

import androidx.annotation.StringRes
import com.yuoyama12.bbsapp.R

enum class ActionsInMoreVert(@StringRes val titleResId: Int) {
    SignUp(R.string.action_sign_up_title),
    Logout(R.string.action_logout_title),
    Settings(R.string.action_settings_title)
}