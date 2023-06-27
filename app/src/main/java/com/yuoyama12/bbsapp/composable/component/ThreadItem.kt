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
import com.yuoyama12.bbsapp.data.Thread
import java.util.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ThreadItem(
    modifier: Modifier = Modifier,
    thread: Thread,
    isFavorite: Boolean,
    showFavoriteIcon: Boolean = true,
    onFavoriteClicked: (threadId: String, favorite: Boolean) -> Unit = { _, _ -> }
) {
    val context = LocalContext.current
    val messageWhenAddToFavorite = stringResource(R.string.add_favorite_message)

    var favorite by remember { mutableStateOf(isFavorite) }
    
    if (favorite) MaterialTheme.colorScheme.primary
    else LocalContentColor.current

    Box(modifier = modifier) {
        Column(modifier = modifier.padding(8.dp)) {
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
            ) {
                Text(
                    text = thread.title,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(0.9f),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                if (showFavoriteIcon) {
                    IconButton(
                        onClick = {
                            if (!favorite) {
                                Toast.makeText(context, messageWhenAddToFavorite, Toast.LENGTH_SHORT).show()
                            }

                            favorite = !favorite
                            onFavoriteClicked(thread.threadId, favorite)
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
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp)
            ) {
                val textColor = Color.Gray

                Text(
                    text = thread.latestMessageBody,
                    modifier = Modifier
                        .weight(0.6f)
                        .padding(horizontal = 6.dp),
                    color = textColor,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = thread.modifiedDate,
                    modifier = Modifier.weight(0.4f),
                    color = textColor,
                    fontSize = 12.sp,
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
            val thread = Thread()
            ThreadItem(
                thread = thread,
                isFavorite = true,
                onFavoriteClicked = { _, _ -> }
            )
        }
    }
}