package com.example.gym.screens.workout

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gym.components.HeaderViewBackButton

@Composable
fun DetailSpreadsheetScreen(
    modifier: Modifier = Modifier,
    detailsSpreadsheetViewModel: WorkoutScreenViewModel,
    navController: NavController,
    spreadsheet: Int
) {
    val spreadsheetDetail by detailsSpreadsheetViewModel.spreadsheetDetail.observeAsState()

    LaunchedEffect(spreadsheet) {
        detailsSpreadsheetViewModel.spreadsheetByid(spreadsheet)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        HeaderViewBackButton(
            title = "Detalhes: ${spreadsheetDetail?.name?.replaceFirstChar { it.titlecase() } ?: ""}",
            onBackClick = {
                navController.popBackStack()
            }
        )
        Spacer(Modifier.height(26.dp))
        Text(
            text = "Exercicios",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(12.dp)
        )

        LazyColumn(
            modifier.fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            spreadsheetDetail?.workouts.run {
                if (isNullOrEmpty()) {
                    item {
                        Text(
                            text = "Nenhum treino encontrado.",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                } else {
                    items(this) { workout ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.onSurface,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = workout.name,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(text = "Séries: ${workout.series}")
                                Text(text = "Repetições: ${workout.reps}")
                                Text(text = "Peso: ${workout.weight} kg")
                            }
                        }
                    }
                }
            }
        }
    }
}