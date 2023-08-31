package com.yuoyama12.bbsapp.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yuoyama12.bbsapp.R

@Composable
fun EmailField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChanged(it) },
        placeholder = {
            Text(text = stringResource(R.string.email_placeholder))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null
            )
        },
        singleLine = true
    )
}

@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String,
    keyboardActions: KeyboardActions =
        KeyboardActions {  },
) {
    var isVisible by remember { mutableStateOf(false) }

    val icon =
        if (isVisible) painterResource(R.drawable.ic_baseline_visibility_24)
        else painterResource(R.drawable.ic_baseline_visibility_off_24)

    val visualTransformation =
        if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChanged(it) },
        placeholder = { Text(placeholder) },
        leadingIcon = {
            Icon(
                Icons.Default.Lock,
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(
                    painter = icon,
                    contentDescription = null
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation
    )
}

@Composable
fun UserNameField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChanged(it) },
        placeholder = {
            Text(text = stringResource(R.string.user_name_placeholder))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null
            )
        },
        singleLine = true
    )
}

val userAccountFieldModifier =
    Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 4.dp)
val userAccountButtonModifier =
    Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
val userAccountButtonFontSize = 16.sp

