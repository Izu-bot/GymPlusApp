package com.example.gym.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CardWorkout(
    title: String,
    style: TextStyle,
    textColor: Color,
    fontWeight: FontWeight,
    cornerRadius: Shape,
    colors: CardColors,
    cardElevation: CardElevation,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClickMenu: () -> Unit
    ) {
    Card(
        shape = cornerRadius,
        colors = colors,
        elevation = cardElevation,
        modifier = modifier.size(width = 160.dp, height = 84.dp),
        onClick = onClick,
        enabled = enabled
    ) {
        Row(
            modifier = modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = style,
                color = textColor,
                fontWeight = fontWeight
            )
            DropdownMenu(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.surface,
                onClick = onClickMenu
            )
        }
    }
}