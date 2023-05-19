package com.yuoyama12.bbsapp.ui.actions

import androidx.annotation.StringRes
import com.yuoyama12.bbsapp.R

object ActionsInMoreVert {
    enum class Actions(@StringRes val titleResId: Int) {
        SignUp(R.string.action_sign_up_title),
        Logout(R.string.action_logout_title),
        Settings(R.string.action_settings_title)
    }

    fun getActionsByState(isLoginAsAnonymous: Boolean): List<Actions> {
        return if (isLoginAsAnonymous) {
            Actions.values().toList()
        } else {
            Actions.values().filter {
                when (it) {
                    Actions.SignUp -> false
                    Actions.Logout -> true
                    Actions.Settings -> true
                }
            }
        }

    }
}