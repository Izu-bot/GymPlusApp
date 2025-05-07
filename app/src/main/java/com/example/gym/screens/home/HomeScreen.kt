package com.example.gym.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun HomeScreen(modifier: Modifier = Modifier, homeScreenViewModel: HomeScreenViewModel, navController: NavController) {

    val name by homeScreenViewModel.nameUser.collectAsState()

    Column(modifier.fillMaxSize()) {
        Text("${homeScreenViewModel.welcomeUser()}, ${name.takeIf { it.isNotEmpty() } ?: "Usuário"}")

    }
}