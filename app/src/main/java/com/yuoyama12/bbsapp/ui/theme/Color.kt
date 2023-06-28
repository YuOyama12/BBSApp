package com.yuoyama12.bbsapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

@Composable
fun userNameTextColor(): Color =
    if (isSystemInDarkTheme()) Color.LightGray
        else Color.DarkGray

@Composable
fun messageBoxColor(): Color =
    if (isSystemInDarkTheme()) Color.DarkGray
        else Color.LightGray

@Composable
fun selfMessageBoxColor(): Color = MaterialTheme.colorScheme.primaryContainer