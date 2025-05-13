package com.example.gym.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gym.components.CardTraining

@Composable
fun HomeScreen(modifier: Modifier = Modifier, homeScreenViewModel: HomeScreenViewModel, navController: NavController) {

    val name by homeScreenViewModel.nameUser.collectAsState()
    val dia by homeScreenViewModel.getDay.collectAsState()

    Column(modifier.fillMaxSize()) {
        Text(
            text = ("${homeScreenViewModel.welcomeUser()}, ${name.takeIf { it.isNotEmpty() } ?: "Usuário"}"),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 15.dp)
        )
        Text(
            text = dia,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        CardTraining(
            cornerRadius = 22.dp,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            cardElevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            borderStroke = BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondaryContainer),
            title = "Treino de hoje",
            subTitle = "Complete suas metas diárias",
            titleButton = "Começar treino"
        )
    }
}