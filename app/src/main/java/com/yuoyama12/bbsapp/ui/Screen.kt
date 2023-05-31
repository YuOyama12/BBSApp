package com.yuoyama12.bbsapp.ui

import com.yuoyama12.bbsapp.*

sealed class Screen(val route: String) {
    object Main: Screen(MAIN_SCREEN)
    object Login: Screen(LOGIN_SCREEN)
    object SignUp: Screen(SIGN_UP_SCREEN)
    object Thread: Screen(THREAD_SCREEN) {
        fun createRouteWithThreadId(id: String) =
            "${Thread.route}/$id"
    }
}
