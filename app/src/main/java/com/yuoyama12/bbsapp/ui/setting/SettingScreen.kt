package com.yuoyama12.bbsapp.ui.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alorma.compose.settings.ui.SettingsGroup
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.yuoyama12.bbsapp.R
import com.yuoyama12.bbsapp.composable.NormalTopAppBar

private val settingsGroupTitleFontSize = 14.sp
private val horizontalDividerThickness = 0.5.dp
@Composable
fun SettingScreen() {
    Column {
        NormalTopAppBar(
            text = stringResource(R.string.setting_screen_app_bar_title)
        )

        Column {
            SettingsGroup(
                title = {
                    Text(
                        text = stringResource(R.string.setting_group_title_about_login_info),
                        fontSize = settingsGroupTitleFontSize
                    )
                }
            ) {
                SettingsMenuLink(
                    title = { Text(text = stringResource(R.string.setting_title_about_changing_login_info)) },
                    onClick = {  },
                )
                HorizontalDivider(thickness = horizontalDividerThickness)

                SettingsMenuLink(
                    title = { Text(text = stringResource(R.string.setting_title_about_deleting_the_account)) },
                    onClick = {  },
                )
                HorizontalDivider(thickness = horizontalDividerThickness)

            }
        }
    }
}