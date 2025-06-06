package com.example.gym.screens.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gym.components.CardWorkout
import com.example.gym.navigation.Destination

@Composable
fun WorkoutScreen(
    modifier: Modifier = Modifier,
    workoutScreenViewModel: WorkoutScreenViewModel,
    navController: NavController
    ) {
    val spreadsheetList by workoutScreenViewModel.spreadsheetList.observeAsState(emptyList())

    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        workoutScreenViewModel.viewSpreadsheet()
    }

    LazyColumn(
        modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(spreadsheetList) { planilha ->
            CardWorkout(
                title = planilha.name.replaceFirstChar { it.titlecase() },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textColor = MaterialTheme.colorScheme.surface,
                cornerRadius = RoundedCornerShape(
                    12.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentColor = MaterialTheme.colorScheme.surface
                ),
                cardElevation = CardDefaults.cardElevation(
                    defaultElevation = 16.dp
                ),
                onClick = {
                    navController.navigate(Destination.DETAILS.route.replace("{spreadsheetId}", "${planilha.id}"))
                },
                onClickMenu = {
                    workoutScreenViewModel.removeSpreadsheet(planilha.id)
                    focusManager.clearFocus()
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(Modifier.height(14.dp))
        }
    }

//    Column(modifier) {
//        Text("Olá Tela de treino")
//
//        spreadsheetList.forEach { planilha ->
//            Text(text = "id: ${planilha.id}")
//            Text(text = "name: ${planilha.name}")
//            planilha.workout?.takeIf { it.isNotEmpty() }?.let { treinos ->
//                treinos.forEach { treino ->
//                    Text(text = "ID do treino: ${treino.id}")
//                    Text(text = "Nome do treino: ${treino.name}")
//                    Text(text = "Séries: ${treino.series}")
//                    Text(text = "Repetições: ${treino.reps}")
//                    Text(text = "Peso: ${treino.weight}")
//                    Spacer(modifier = Modifier.height(8.dp))
//                }
//            } ?: run {
//                Text(text = "Não há treinos cadastrados")
//            }
//            Spacer(Modifier.height(20.dp))
//        }
//    }
}