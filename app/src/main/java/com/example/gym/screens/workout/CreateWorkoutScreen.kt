package com.example.gym.screens.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gym.components.HeaderViewBackButton
import com.example.gym.components.LabeledTextField
import com.example.gym.components.MyButton
import com.example.gym.components.MyTextField
import com.example.gym.components.SelectTextField
import com.example.gym.navigation.Destination
import org.w3c.dom.Text

@Composable
fun CreateWorkoutScreen(
    modifier: Modifier = Modifier,
    createWorkoutScreenViewModel: WorkoutScreenViewModel,
    navController: NavController
    ) {
    val focusManager = LocalFocusManager.current

    val treinos = listOf("Peito", "Costas", "Pernas", "Ombro", "Bíceps", "Tríceps")
    var selectedTreino by remember { mutableStateOf(treinos[0]) }


    Column {
        HeaderViewBackButton(
            title = "Adicionar Exercicio",
            onBackClick = {
                navController.navigate(Destination.HOME.route)
            }
        )
        Card(modifier = Modifier.padding(16.dp)) {
            LabeledTextField(
                label = "Nome do exercicio",
                value = "",
                onValueChange = { },
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
                    value = "",
                    onValueChange = {},
                    placeholder = "48",
                    label = "Peso (kg)",
                    icon = { Icon(Icons.Default.Star, contentDescription = "Start") },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    modifier = Modifier.weight(0.6f)
                )
                MyTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = "48",
                    label = "Peso (kg)",
                    icon = { Icon(Icons.Default.Star, contentDescription = "Start") },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    modifier = Modifier.weight(0.6f)
                )

            }
            MyTextField(
                value = "",
                onValueChange = {},
                placeholder = "48",
                label = "Peso (kg)",
                icon = { Icon(Icons.Default.Star, contentDescription = "Start") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )

            SelectTextField(
                selectedValue = selectedTreino,
                options = treinos,
                label = "Associe seu exercicio a uma planilha",
                onValueChangedEvent = {},
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