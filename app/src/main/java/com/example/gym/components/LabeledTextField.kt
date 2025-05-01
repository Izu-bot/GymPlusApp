package com.example.gym.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gym.R
import androidx.compose.ui.graphics.Shape

@Composable
fun LabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    textStyle: TextStyle = TextStyle(fontSize = 18.sp),
    shape: Shape = RoundedCornerShape(14.dp),
    maxLines: Int = 1,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    enabled: Boolean = true,
    isError: Boolean = false,
    supportingText: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier) {
        var passwordVisible by remember { mutableStateOf(false) }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            placeholder = {
                Text(
                    text = placeholder
                )
            },
            textStyle = textStyle,
            shape = shape,
            maxLines = maxLines,
            colors = colors,
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                if (isPassword) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        val icon = if (passwordVisible) R.drawable.visibility else R.drawable.visibility_off
                        val description = if (passwordVisible) "Hide password" else "Show password"
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = description
                        )
                    }
                }
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            modifier = Modifier.fillMaxWidth().padding(15.dp),
            enabled = enabled,
            isError = isError,
            supportingText = supportingText
        )
    }
}

