package com.yuoyama12.bbsapp.component

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yuoyama12.bbsapp.R
import com.yuoyama12.bbsapp.ui.theme.BBSAppTheme
import com.yuoyama12.bbsapp.ui.theme.messageBackgroundColor
import com.yuoyama12.bbsapp.ui.theme.userNameTextColor

@Composable
fun Message(
    modifier: Modifier = Modifier,
    userIcon: Painter,
    userName: String,
    messageText: String
) {
    Row(modifier = modifier.padding(8.dp)) {
        Image(
            painter = userIcon,
            contentDescription = null,
            modifier = Modifier
                .height(40.dp)
                .weight(0.1f)
                .clip(CircleShape),
            contentScale = ContentScale.Crop

        )

        Column(modifier = Modifier.weight(0.9f)) {
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
                    .background(color = messageBackgroundColor(), shape = RoundedCornerShape(6.dp))
            ) {
                Text(
                    text = messageText,
                    modifier = Modifier.padding(8.dp)
                )
            }
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
            Message(
                messageText = "",
                userName = "anonymous",
                userIcon = painterResource(R.drawable.ic_baseline_person_24)
            )
        }
    }
}
