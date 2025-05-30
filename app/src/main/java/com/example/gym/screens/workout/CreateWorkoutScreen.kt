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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.example.gym.R
import com.example.gym.components.HeaderViewBackButton
import com.example.gym.components.LabeledTextField
import com.example.gym.components.MyButton
import com.example.gym.components.MyTextField
import com.example.gym.components.SelectTextField
import com.example.gym.navigation.Destination
import kotlinx.coroutines.launch

@Composable
fun CreateWorkoutScreen(
    modifier: Modifier = Modifier,
    createWorkoutScreenViewModel: WorkoutScreenViewModel,
    navController: NavController
    ) {
    val focusManager = LocalFocusManager.current

    val nameWorkout by createWorkoutScreenViewModel.nameWorkout.observeAsState("")
    val repsWorkout by createWorkoutScreenViewModel.repsWorkout.observeAsState("")
    val seriesWorkout by createWorkoutScreenViewModel.seriesWorkout.observeAsState("")
    val weightWorkout by createWorkoutScreenViewModel.weightWorkout.observeAsState("")

    val spredasheetList by createWorkoutScreenViewModel.spreadsheetList.observeAsState(emptyList())
    var selectedPlanilha by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current



    LaunchedEffect(key1 = lifecycleOwner.lifecycle, key2 = snackbarHostState) {
        createWorkoutScreenViewModel.viewSpreadsheet()

        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            createWorkoutScreenViewModel.navigationAndStatusEvent.collect { navigationEvent ->
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

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
    Column(modifier
        .padding(paddingValues)
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            focusManager.clearFocus()
        }) {
        HeaderViewBackButton(
            title = "Adicionar Exercicio",
            onBackClick = {
                navController.navigate(Destination.HOME.route)
            }
        )
        Card(modifier = Modifier.padding(16.dp)) {
            LabeledTextField(
                label = "Nome do Exercicio",
                value = nameWorkout,
                onValueChange = { createWorkoutScreenViewModel.onChangeNameWorkout(it) },
                placeholder = "ex, Supino Reto",
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                }
            )
            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.padding(20.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                MyTextField(
                    value = repsWorkout,
                    onValueChange = { createWorkoutScreenViewModel.onChangeRepsWorkout(it)},
                    placeholder = "12",
                    label = "Repetições",
                    icon = { Icon(painterResource(id = R.drawable.cycle_46dp_), contentDescription = "Repetições") },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.weight(0.6f)
                )
                MyTextField(
                    value = seriesWorkout,
                    onValueChange = { createWorkoutScreenViewModel.onChangeSeriesWorkout(it) },
                    placeholder = "3",
                    label = "Series",
                    icon = { Icon(painterResource(id = R.drawable.steppers_46dp_), contentDescription = "Series") },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.weight(0.6f)
                )

            }
            MyTextField(
                value = weightWorkout,
                onValueChange = { createWorkoutScreenViewModel.onChangeWeightWorkout(it) },
                placeholder = "48",
                label = "Peso (kg)",
                icon = { Icon(
                    painterResource(id = R.drawable.weight_46dp_),
                    contentDescription = "Peso",
                ) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),

                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )

            SelectTextField(
                selectedValue = selectedPlanilha,
                options = spredasheetList,
                label = "Associe seu exercicio a uma planilha",
                onValueChangedEvent = { selectedName ->
                    selectedPlanilha = selectedName

                    val selectedId = spredasheetList.firstOrNull { it.name == selectedName}?.id ?: 0

                    createWorkoutScreenViewModel.onChangeSpreadsheetId(selectedId)
                },
                modifier = Modifier.padding(16.dp)
            )

            Spacer(Modifier.height(26.dp))
        }

        MyButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .size(55.dp),
            text = "Adicionar",
            onClick = {
                focusManager.clearFocus()
                createWorkoutScreenViewModel.createWorkout()
                createWorkoutScreenViewModel.clearInputs()

            },
            fontSize = 18.sp,
            buttonColors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onSurface,
                contentColor = MaterialTheme.colorScheme.surface,
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(),
        )
    }
}
}