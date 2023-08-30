package com.yuoyama12.bbsapp.ui

import com.yuoyama12.bbsapp.*

sealed class Screen(val route: String) {
    object Main : Screen(MAIN_SCREEN)
    object Login : Screen(LOGIN_SCREEN)
    object SignUp : Screen(SIGN_UP_SCREEN)
    object Thread : Screen(THREAD_SCREEN) {
        fun createRouteWithThreadId(id: String) =
            "${Thread.route}/$id"
    }
    object Setting : Screen(SETTING_SCREEN)
    object VerifyPasswordForChangingMailAddress : Screen(VERIFY_PASSWORD_SCREEN + "forChangingMailAddress")
    object VerifyPasswordForChangingPassword : Screen(VERIFY_PASSWORD_SCREEN + "forChangingPassword")

    object ChangeMailAddress : Screen(CHANGE_MAIL_ADDRESS_SCREEN)
    object ChangePassword : Screen(CHANGE_PASSWORD_SCREEN)

}
