package com.yuoyama12.bbsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yuoyama12.bbsapp.ui.Screen
import com.yuoyama12.bbsapp.ui.login.LoginScreen
import com.yuoyama12.bbsapp.ui.login.LoginViewModel
import com.yuoyama12.bbsapp.ui.main.MainScreen
import com.yuoyama12.bbsapp.ui.main.MainViewModel
import com.yuoyama12.bbsapp.ui.theme.BBSAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val loginViewModel: LoginViewModel = hiltViewModel()

            BBSAppTheme {
                val navController = rememberNavController()
                val startDestination =
                    if (loginViewModel.isNotLogin() || mainViewModel.isFirstBoot) Screen.Login.route
                    else Screen.Main.route

                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {
                    composable(Screen.Main.route) { MainScreen() }
                    composable(Screen.Login.route) {
                        LoginScreen(
                            onLoginAsGuestClicked = {
                                mainViewModel.setIsFirstBoot(false)
                                loginViewModel.loginAsAnonymous()

                                navController.navigate(Screen.Main.route)
                            }
                        )
                    }
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BBSAppTheme {
        MainScreen()
    }
}