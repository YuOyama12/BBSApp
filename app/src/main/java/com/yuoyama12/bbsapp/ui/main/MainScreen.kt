package com.yuoyama12.bbsapp.ui.main

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yuoyama12.bbsapp.R
import com.yuoyama12.bbsapp.DEFAULT_THREAD_ID
import com.yuoyama12.bbsapp.THREAD_ID
import com.yuoyama12.bbsapp.composable.NormalActionTopAppBar
import com.yuoyama12.bbsapp.composable.SimplePopupMenu
import com.yuoyama12.bbsapp.composable.component.VisibleAppBarScaffold
import com.yuoyama12.bbsapp.ui.NavScreen
import com.yuoyama12.bbsapp.ui.Screen
import com.yuoyama12.bbsapp.ui.actions.ActionsInMoreVert
import com.yuoyama12.bbsapp.ui.favorite.FavoriteScreen
import com.yuoyama12.bbsapp.ui.thread.ThreadScreen
import com.yuoyama12.bbsapp.ui.threadslist.ThreadsListScreen

@Composable
fun MainScreen(
    moveToSignUpScreen: () -> Unit,
    moveToLoginScreen: () -> Unit,
    moveToSettingScreen: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: MainViewModel = hiltViewModel()

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
                    val actions = ActionsInMoreVert.getActionsByState(viewModel.isLoginAsAnonymous)

                    SimplePopupMenu(
                        menuItemContent = actions,
                        menuItemTitles = actions.map { stringResource(it.titleResId) },
                        expanded = expandMenuOnAppBar,
                        onDismissRequest = { expandMenuOnAppBar = false },
                        onMenuItemClicked = { content ->
                            when (content) {
                                ActionsInMoreVert.Actions.SignUp -> { moveToSignUpScreen() }
                                ActionsInMoreVert.Actions.Logout -> {
                                    viewModel.logout()

                                    Toast.makeText(
                                        context,
                                        R.string.logout_completed_message,
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    moveToLoginScreen()
                                }
                                ActionsInMoreVert.Actions.Settings -> { moveToSettingScreen() }
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
                ThreadsListScreen(
                    onItemClicked = { threadId ->
                        if (backStackEntry.lifecycle.currentState == Lifecycle.State.RESUMED)
                            navController.navigate(Screen.Thread.createRouteWithThreadId(threadId)) {
                                launchSingleTop = true
                            }
                    }
                )
            }
            composable(NavScreen.Favorite.route) { backStackEntry ->
                FavoriteScreen(
                    onItemClicked = { threadId ->
                        if (backStackEntry.lifecycle.currentState == Lifecycle.State.RESUMED)
                            navController.navigate(Screen.Thread.createRouteWithThreadId(threadId)) {
                                launchSingleTop = true
                            }
                    }
                )
            }

            composable(
                route = "${Screen.Thread.route}/{$THREAD_ID}",
                arguments = listOf(navArgument(THREAD_ID) { type = NavType.StringType })
            ) { backStackEntry ->
                val threadId = backStackEntry.arguments?.getString(THREAD_ID) ?: DEFAULT_THREAD_ID

                ThreadScreen(
                    threadId = threadId,
                    onNavigationIconClicked = { navController.popBackStack() }
                )
            }
        }
    }
}