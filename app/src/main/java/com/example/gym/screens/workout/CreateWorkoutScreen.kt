package com.example.gym.screens.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gym.components.LabeledTextField

@Composable
fun CreateWorkoutScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    val groupsMuscle = listOf(
        "Peito",
        "Perna",
        "Costas",
        "Braço",
        "Quadriceps",
        "Posteriores",
        "Ombro"
    )

    Column(modifier) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Votlar a Home")
            Text(text = "Criar planilha de treino", style = MaterialTheme.typography.titleMedium)
        }
        Spacer(Modifier.height(16.dp))
        LabeledTextField(
            label = "Nome da Planilha",
            value = "",
            onValueChange = {},
            placeholder = "ex, Treino de Segunda-feira",
        )

//        Seção para criar planilha
        Text(
            text = "Grupo Muscular",
            modifier = Modifier.padding(5.dp)
        )
        LazyRow {
            items(groupsMuscle) {
                Card(
                    modifier = Modifier
                        .size(width = 100.dp, height = 60.dp)
                        .padding(5.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                ) { Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = it, textAlign = TextAlign.Center)
                } }
            }
        }

//        Sessão para visualizar os treinos
        Text(
            text = "Seus Treinos",
            modifier = Modifier.padding(5.dp)
        )

//        Botão para criar exercicio
//        Botão para criar a planilha
    }
}