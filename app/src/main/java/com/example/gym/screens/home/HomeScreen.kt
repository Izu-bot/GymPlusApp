package com.example.gym.screens.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gym.R
import com.example.gym.components.CardComponent
import com.example.gym.components.CardTraining
import com.example.gym.navigation.Destination

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeScreenViewModel: HomeScreenViewModel,
    navController: NavController
) {

    val name by homeScreenViewModel.nameUser.collectAsState()
    val dia by homeScreenViewModel.getDay.collectAsState()
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "message/rfc822"
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("kauanmartins977@gmail.com"))
                        putExtra(Intent.EXTRA_SUBJECT, "Feedback do App")
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Olá, gostaria de sugerir as seguintes melhorias:\n\n"
                        )
                    }
                    try {
                        context.startActivity(Intent.createChooser(intent, "Enviar feedback por"))
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(
                            context,
                            "Nenhum cliente de email instalado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Enviar feedback"
                )
            }
        }
    ) { paddingValues ->
        Column(modifier
            .fillMaxSize()
            .padding(paddingValues)) {
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
                cornerRadius = 12.dp,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                cardElevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                title = "Treino de hoje",
                subTitle = "Complete suas metas diárias",
                titleButton = "Começar treino",
                isEnabled = false
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
//                    .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CardComponent(
                        icon = R.drawable.add_80dp,
                        title = "Criar Treino",
                        style = MaterialTheme.typography.titleSmall,
                        textColor = MaterialTheme.colorScheme.secondary,
                        cornerRadius = 12.dp,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        cardElevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        onClick = {
                            navController.navigate(Destination.CREATE_SPREADSHEET.route) {
                                launchSingleTop = true
                            }
                        }
                    )
                    CardComponent(
                        enabled = false,
                        icon = R.drawable.trading,
                        title = "Progresso",
                        style = MaterialTheme.typography.titleSmall,
                        textColor = MaterialTheme.colorScheme.secondary,
                        cornerRadius = 12.dp,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        cardElevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        onClick = {
                        }
                    )
                }
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                ) {
                    CardComponent(
                        modifier = Modifier.fillMaxWidth(),
                        icon = R.drawable.add_80dp,
                        title = "Adicionar Exercicio",
                        style = MaterialTheme.typography.titleSmall,
                        textColor = MaterialTheme.colorScheme.secondary,
                        cornerRadius = 12.dp,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        cardElevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        onClick = {
                            navController.navigate(Destination.CREATE_WORKOUT.route)
                        }
                    )
                }

            }
        }
    }
}