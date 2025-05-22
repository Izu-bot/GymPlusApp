package com.example.gym.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CardComponent(
    icon: Int,
    title: String,
    style: TextStyle,
    textColor: Color,
    cornerRadius: Dp,
    colors: CardColors,
    cardElevation: CardElevation,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(cornerRadius),
        colors = colors,
        elevation = cardElevation,
        modifier = Modifier.size(width = 160.dp, height = 84.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Icon(painter = painterResource(id = icon), contentDescription = title)
            Spacer(Modifier.height(2.dp))
            Text(
                text = title,
                style = style,
                color = textColor
            )
        }
    }
}