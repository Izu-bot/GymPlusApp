package com.example.gym.screens.workout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.example.gym.components.HeaderViewBackButton
import com.example.gym.components.LabeledTextField
import com.example.gym.components.MyButton
import com.example.gym.navigation.Destination
import kotlinx.coroutines.launch

@Composable
fun CreateSpreadsheetScreen(
    modifier: Modifier = Modifier,
    workoutScreenViewModel: WorkoutScreenViewModel,
    navController: NavController
) {
    val focusManager = LocalFocusManager.current
    val nomePlanilha by workoutScreenViewModel.namePlanilha.observeAsState("")

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = lifecycleOwner.lifecycle, key2 = snackbarHostState) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            workoutScreenViewModel.navigationAndStatusEvent.collect { navigationEvent ->
                when(navigationEvent) {
                    is NavigationEvent.ShowStatusMessage -> {
                        val message = navigationEvent.message

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = message,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
            }
        }
    }

//    TODO: Adicionar um label para grupo muscular

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
        Column(modifier
            .padding(paddingValues)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
                ) {
                    focusManager.clearFocus()
                },) {
            HeaderViewBackButton(
                title = "Criar planilha de treinos",
                onBackClick = {
                    navController.navigate(Destination.HOME.route)
                }
            )

            Spacer(Modifier.height(16.dp))
            LabeledTextField(
                label = "Nome da Planilha",
                value = nomePlanilha,
                onValueChange = { workoutScreenViewModel.onChangeNamePlanilha(it) },
                placeholder = "ex, Treino de Peito",
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                }

            )

//        Botão para criar a planilha
            MyButton(
                text = "Criar Planilha",
                fontSize = 18.sp,
                onClick = {
                    focusManager.clearFocus()
                    workoutScreenViewModel.criarPlanilha()
                    workoutScreenViewModel.limparNomePlanilha()
                },
                buttonColors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onSurface,
                    contentColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .size(55.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Sua planilha de treino pode ser visualizada na tela \"Treinos\".",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}