package com.example.gym.screens.workout

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun WorkoutScreen(
    modifier: Modifier = Modifier,
    workoutScreenViewModel: WorkoutScreenViewModel,
    navController: NavController
    ) {
    Box(modifier) {
        Text("Olá Tela de treino")
    }
}