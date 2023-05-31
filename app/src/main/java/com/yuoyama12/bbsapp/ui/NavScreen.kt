package com.yuoyama12.bbsapp.ui

import androidx.annotation.StringRes
import com.yuoyama12.bbsapp.FAVORITE_SCREEN
import com.yuoyama12.bbsapp.R
import com.yuoyama12.bbsapp.THREADS_LIST_SCREEN

sealed class NavScreen(val route: String, @StringRes val resourceId: Int) {
    object ThreadsList: NavScreen(THREADS_LIST_SCREEN, R.string.threads_list_navigation_label)
    object Favorite: NavScreen(FAVORITE_SCREEN, R.string.favorite_navigation_label)
}
