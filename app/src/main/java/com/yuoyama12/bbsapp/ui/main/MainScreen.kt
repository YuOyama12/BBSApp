package com.yuoyama12.bbsapp.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yuoyama12.bbsapp.composable.component.VisibleNavigationBarScaffold
import com.yuoyama12.bbsapp.ui.NavScreen
import com.yuoyama12.bbsapp.ui.Screen
import com.yuoyama12.bbsapp.ui.favorite.Favorite
import com.yuoyama12.bbsapp.ui.thread.Thread
import com.yuoyama12.bbsapp.ui.threadslist.ThreadsList

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navigationItems = listOf(NavScreen.ThreadsList, NavScreen.Favorite)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showNavigationBar = navController.currentBackStackEntryAsState().value?.destination?.route in navigationItems.map { it.route }

    VisibleNavigationBarScaffold(
        showNavigationBar = showNavigationBar,
        topBar = {
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
                        if (backStackEntry.getLifecycle().currentState == Lifecycle.State.RESUMED)
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