package com.yuoyama12.bbsapp.ui

sealed class Screen(val route: String) {
    object Thread: Screen("thread")
}
