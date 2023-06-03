package com.yuoyama12.bbsapp.composable.component

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yuoyama12.bbsapp.R
import com.yuoyama12.bbsapp.ui.theme.BBSAppTheme
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ThreadItem(
    modifier: Modifier = Modifier,
    title: String,
    latestMessage: String,
    updatedDate: String,
    isInFavorite: Boolean
) {
    val context = LocalContext.current
    val messageWhenAddToFavorite = stringResource(R.string.add_favorite_message)

    var favorite by remember { mutableStateOf(isInFavorite) }
    
    if (favorite) MaterialTheme.colorScheme.primary
    else LocalContentColor.current

    Box(modifier = modifier) {
        Column(modifier = modifier.padding(12.dp)) {
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(0.9f),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                IconButton(
                    onClick = {
                        Toast.makeText(context, messageWhenAddToFavorite, Toast.LENGTH_SHORT).show()
                        favorite = !favorite
                    },
                    modifier = Modifier
                        .align(Alignment.Top)
                        .weight(0.1f)
                ) {
                    AnimatedContent(
                        targetState = favorite,
                        transitionSpec = { scaleIn(initialScale = 0.4f) with scaleOut(targetScale = 0.4f) }
                    ) {
                        when (it) {
                            true -> {
                                Icon(
                                    imageVector = Icons.Outlined.Favorite,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                )
                            }
                            false -> {
                                Icon(
                                    imageVector = Icons.Outlined.FavoriteBorder,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp)
            ) {
                val textColor = Color.Gray
                val textSize = 10.sp

                Text(
                    text = latestMessage,
                    modifier = Modifier
                        .weight(0.85f)
                        .padding(horizontal = 6.dp, vertical = 4.dp),
                    color = textColor,
                    fontSize = textSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = updatedDate,
                    modifier = Modifier.weight(0.15f),
                    color = textColor,
                    fontSize = textSize,
                    textAlign = TextAlign.End
                )
            }
        }
    }

}

@SuppressLint("SimpleDateFormat")
@Composable
@Preview(name = "normalMode", showBackground = true)
@Preview(name = "darkMode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
fun ThreadPreview() {
    BBSAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            val updatedDate = SimpleDateFormat("MM/dd")
            ThreadItem(
                title = "hogehogehoge",
                latestMessage = "hogehogehoge",
                updatedDate = updatedDate.format(Calendar.getInstance().time),
                isInFavorite = false
            )
        }
    }
}