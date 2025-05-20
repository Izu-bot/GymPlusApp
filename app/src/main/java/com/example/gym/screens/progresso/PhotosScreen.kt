package com.example.gym.screens.progresso

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun PhotosScreen(
    modifier: Modifier = Modifier,
    photosScreenViewModel: PhotosScreenViewModel,
    navController: NavController
) {
    Box(modifier) {
        Text("Tela Fotos")
    }
}