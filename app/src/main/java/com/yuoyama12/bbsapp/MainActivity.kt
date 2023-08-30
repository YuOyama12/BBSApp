package com.yuoyama12.bbsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.yuoyama12.bbsapp.ui.Screen
import com.yuoyama12.bbsapp.ui.login.LoginScreen
import com.yuoyama12.bbsapp.ui.main.MainScreen
import com.yuoyama12.bbsapp.ui.setting.SettingScreen
import com.yuoyama12.bbsapp.ui.settingitem.changemailaddress.ChangeMailAddressScreen
import com.yuoyama12.bbsapp.ui.signup.SignUpScreen
import com.yuoyama12.bbsapp.ui.theme.BBSAppTheme
import com.yuoyama12.bbsapp.ui.verifypassword.VerifyPasswordScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            BBSAppTheme {
                val navController = rememberNavController()
                val startDestination =
                    if (auth.currentUser == null) Screen.Login.route
                    else Screen.Main.route

                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {
                    composable(Screen.Main.route) {
                        MainScreen(
                            moveToSignUpScreen = { navController.navigate(Screen.SignUp.route) },
                            moveToLoginScreen = {
                                navController.popBackStack()
                                navController.navigate(Screen.Login.route) {
                                    popUpTo(Screen.Login.route) {
                                        inclusive = true
                                    }
                                }
                            },
                            moveToSettingScreen = { navController.navigate(Screen.Setting.route) }
                        )
                    }
                    composable(Screen.Login.route) {
                        LoginScreen(
                            isOnTop =
                            navController.currentBackStackEntryAsState().value?.destination?.route == Screen.Login.route,
                            moveToMainScreen = {
                                navController.popBackStack()
                                navController.navigate(Screen.Main.route)
                            },
                            onCreateAccountClicked = { navController.navigate(Screen.SignUp.route) },
                            popBackStack = { navController.popBackStack() }
                        )
                    }
                    composable(Screen.SignUp.route) {
                        SignUpScreen(
                            moveToMainScreen = {
                                navController.popBackStack()
                                navController.navigate(Screen.Main.route) {
                                    popUpTo(Screen.Login.route) {
                                        inclusive = true
                                    }
                                }
                            },
                            popBackStack = { navController.popBackStack() }
                        )
                    }
                    composable(Screen.Setting.route) { backStackEntry ->
                        SettingScreen(
                            moveTo = { screen ->
                                if (backStackEntry.lifecycle.currentState == Lifecycle.State.RESUMED) {
                                    navController.navigate(screen.route)
                                }
                            }
                        )
                    }
                    composable(Screen.VerifyPasswordForChangingMailAddress.route) {
                        VerifyPasswordScreen(
                            onVerifySuccess = { navController.navigate(Screen.ChangeMailAddress.route) }
                        )
                    }
                    composable(Screen.ChangeMailAddress.route) {
                        ChangeMailAddressScreen (
                            moveToMainScreen = {
                                navController.popBackStack()
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
        MainScreen(
            moveToSignUpScreen = {  },
            moveToLoginScreen = {  },
            moveToSettingScreen = {  }
        )
    }
}