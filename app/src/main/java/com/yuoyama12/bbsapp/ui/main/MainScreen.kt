package com.yuoyama12.bbsapp.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yuoyama12.bbsapp.R
import com.yuoyama12.bbsapp.composable.NormalActionTopAppBar
import com.yuoyama12.bbsapp.composable.SimplePopupMenu
import com.yuoyama12.bbsapp.composable.component.VisibleAppBarScaffold
import com.yuoyama12.bbsapp.ui.NavScreen
import com.yuoyama12.bbsapp.ui.Screen
import com.yuoyama12.bbsapp.ui.actions.ActionsInMoreVert
import com.yuoyama12.bbsapp.ui.favorite.Favorite
import com.yuoyama12.bbsapp.ui.thread.Thread
import com.yuoyama12.bbsapp.ui.threadslist.ThreadsList

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    if (viewModel.isFirstBoot) { viewModel.setIsFirstBoot(false) }

    val navController = rememberNavController()
    val navigationItems = listOf(NavScreen.ThreadsList, NavScreen.Favorite)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showAppBar = navController.currentBackStackEntryAsState().value?.destination?.route in navigationItems.map { it.route }
    var expandMenuOnAppBar by rememberSaveable { mutableStateOf(false) }

    VisibleAppBarScaffold(
        showAppBar = showAppBar,
        topBar = {
            NormalActionTopAppBar(
                text = stringResource(R.string.app_name),
                action = { expandMenuOnAppBar = !expandMenuOnAppBar },
                actionIcon = Icons.Default.MoreVert,
                popupMenu = {
                    SimplePopupMenu(
                        menuItemContent = ActionsInMoreVert.values(),
                        menuItemTitles = ActionsInMoreVert.values().map { stringResource(it.titleResId) },
                        expanded = expandMenuOnAppBar,
                        onDismissRequest = { expandMenuOnAppBar = false },
                        onMenuItemClicked = { content ->
                            when (content) {
                                ActionsInMoreVert.SignUp -> {  }
                                ActionsInMoreVert.Settings -> {  }
                            }
                        }
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar {
                navigationItems.forEach { screen ->
                    NavigationBarItem(
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {  }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = NavScreen.ThreadsList.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(NavScreen.ThreadsList.route) { backStackEntry ->
                ThreadsList(
                    onItemClicked = {
                        if (backStackEntry.lifecycle.currentState == Lifecycle.State.RESUMED)
                            navController.navigate(Screen.Thread.route) {
                                launchSingleTop = true
                            }
                    }
                )
            }
            composable(NavScreen.Favorite.route) { Favorite() }

            composable(Screen.Thread.route) {
                Thread(
                    threadTitle = "test",
                    onNavigationIconClicked = { navController.popBackStack() }
                )
            }
        }
    }
}