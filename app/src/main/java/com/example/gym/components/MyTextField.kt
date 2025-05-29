package com.example.gym.components

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MyTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    label: String,
    icon: @Composable () -> Unit,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
    ) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = placeholder)
        },
        label = {
            Text( text = label)
        },
        leadingIcon = icon,
        colors = colors,
        modifier = modifier
    )
}