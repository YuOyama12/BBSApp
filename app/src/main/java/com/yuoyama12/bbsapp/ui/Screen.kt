package com.yuoyama12.bbsapp.ui

sealed class Screen(val route: String) {
    object Main: Screen("main")
    object Login: Screen("login")
    object SignUp: Screen("signup")
    object Thread: Screen("thread")
}
