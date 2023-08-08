package com.yuoyama12.bbsapp.composable.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yuoyama12.bbsapp.R
import com.yuoyama12.bbsapp.ui.theme.BBSAppTheme
import com.yuoyama12.bbsapp.ui.theme.messageBoxColor
import com.yuoyama12.bbsapp.ui.theme.selfMessageBoxColor
import com.yuoyama12.bbsapp.ui.theme.userNameTextColor

private val messageRowPadding = 8.dp
private const val ICON_WEIGHT = 0.1f
private const val BOX_WEIGHT = 1 - ICON_WEIGHT
@Composable
fun MessageItem(
    modifier: Modifier = Modifier,
    userIcon: Painter,
    userName: String,
    messageText: String
) {
    Row(modifier = modifier.padding(messageRowPadding)) {
        UserIcon(
            modifier = Modifier.weight(ICON_WEIGHT),
            userIcon = userIcon
        )

        UserTextBox(
            modifier = Modifier.weight(BOX_WEIGHT),
            userName = userName,
            messageText = messageText,
            messageBoxColor = messageBoxColor()
        )
    }
}

@Composable
fun FLipHorizontalMessageItem(
    modifier: Modifier = Modifier,
    userIcon: Painter,
    userName: String,
    messageText: String
) {
    Row(modifier = modifier.padding(messageRowPadding)) {
        UserTextBox(
            modifier = Modifier.weight(BOX_WEIGHT, false),
            userName = userName,
            messageText = messageText,
            messageBoxColor = selfMessageBoxColor(),
            isFLipHorizontal = true
        )

        UserIcon(
            modifier = Modifier.weight(ICON_WEIGHT),
            userIcon = userIcon
        )
    }
}

@Composable
private fun UserIcon(
    modifier: Modifier = Modifier,
    userIcon: Painter
) {
    Image(
        painter = userIcon,
        contentDescription = null,
        modifier = modifier
            .height(40.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun UserTextBox(
    modifier: Modifier = Modifier,
    userName: String,
    messageText: String,
    messageBoxColor: Color,
    isFLipHorizontal: Boolean = false
) {
    Column(
        modifier = modifier,
        horizontalAlignment = if (isFLipHorizontal) Alignment.End else Alignment.Start
    ) {
        Text(
            text = userName,
            modifier = Modifier.padding(start = 2.dp, end = 6.dp),
            color = userNameTextColor(),
            fontSize = 10.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        Box(
            modifier = Modifier
                .widthIn(min = 48.dp)
                .heightIn(min = 32.dp)
                .padding(4.dp)
                .background(color = messageBoxColor, shape = RoundedCornerShape(6.dp))
        ) {
            Text(
                text = messageText,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
@Preview(name = "normalMode", showBackground = true)
@Preview(name = "darkMode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
fun MessagePreview() {
    BBSAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            MessageItem(
                messageText = "",
                userName = "anonymous",
                userIcon = painterResource(R.drawable.ic_baseline_person_24)
            )
        }
    }
}
