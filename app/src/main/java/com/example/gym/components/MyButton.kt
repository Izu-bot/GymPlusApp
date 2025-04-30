package com.example.gym.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit

@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    fontSize: TextUnit,
    buttonColors: ButtonColors,
    elevation: ButtonElevation,
    shape: RoundedCornerShape
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = buttonColors,
        elevation = elevation,
        shape = shape
    ) {
        Text(
            text = text,
            fontSize = fontSize
            )
    }
}