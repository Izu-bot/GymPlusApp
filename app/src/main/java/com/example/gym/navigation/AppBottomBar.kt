package com.example.gym.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onItemClick: (Destination) -> Unit
) {
    val visibleDestination = listOf(
        Destination.HOME,
        Destination.WORKOUT,
        Destination.PHOTOS,
        Destination.MEDICAO,
    ) // ROTAS

    NavigationBar {
        visibleDestination.forEach { destination ->
            val isEnabled = destination != Destination.PHOTOS && destination != Destination.MEDICAO
            NavigationBarItem(
                enabled = isEnabled,
                selected = currentRoute == destination.route,
                onClick = { onItemClick(destination) },
                icon = { Icon(painter = painterResource(id = destination.icon), contentDescription = destination.label) },
                label = { Text(destination.label, textAlign = TextAlign.Center) }
            )
        }
    }
}