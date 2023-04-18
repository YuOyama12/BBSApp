package com.yuoyama12.bbsapp.ui

import androidx.annotation.StringRes
import com.yuoyama12.bbsapp.R

sealed class NavScreen(val route: String, @StringRes val resourceId: Int) {
    object ThreadsList: NavScreen("threadsList", R.string.threads_list_navigation_label)
    object Favorite: NavScreen("favorite", R.string.favorite_navigation_label)
}
