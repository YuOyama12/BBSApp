package com.yuoyama12.bbsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yuoyama12.bbsapp.ui.login.LoginScreen
import com.yuoyama12.bbsapp.ui.main.MainScreen
import com.yuoyama12.bbsapp.ui.main.MainViewModel
import com.yuoyama12.bbsapp.ui.theme.BBSAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        setContent {
            BBSAppTheme {
                if (auth.currentUser == null && viewModel.isFirstBoot) {
                    viewModel.setIsFirstBoot(false)

                    Surface(
                        color = MaterialTheme.colorScheme.background
                    ) {
                        LoginScreen()
                    }
                } else {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MainScreen()
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