package com.example.gym.screens.medicao

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun MedicaoScreen(
    modifier: Modifier = Modifier,
    medicaoScreenViewModel: MedicaoScreenViewModel,
    navController: NavController
) {
    Box {
        Text("Oi Medição")
    }
}