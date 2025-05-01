package com.example.gym.screens.cadastro

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gym.components.HeaderViewBackButton
import com.example.gym.components.LabeledTextField
import com.example.gym.components.MyButton

@Composable
fun CadastroScreen(modifier: Modifier = Modifier, cadastroScreenViewModel: CadastroScreenViewModel, navController: NavController) {

    val email by cadastroScreenViewModel.emailLogin.observeAsState(initial = "")
    val name by cadastroScreenViewModel.nameLogin.observeAsState(initial = "")
    val password by cadastroScreenViewModel.passwordLogin.observeAsState(initial = "")
    val confirmPassword by cadastroScreenViewModel.confirmPassword.observeAsState(initial = "")
    val isLoading by cadastroScreenViewModel.isLoading.observeAsState(initial = false)

    val focusManager = LocalFocusManager.current

    Column(modifier
        .fillMaxSize()
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
    ) {
        focusManager.clearFocus()
    }) {
        HeaderViewBackButton(
            title = "Criar uma conta",
            onBackClick = {
                navController.navigate("login")
            }
        )

        Spacer(modifier = Modifier.padding(top = 20.dp))
        Column(modifier = Modifier.padding(start = 20.dp)) {
            Text(
                text = "Junte-se a nós hoje",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Crie sua conta para começar",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.padding(top = 5.dp))

        LabeledTextField(
            label = "Nome",
            value = name,
            onValueChange = {cadastroScreenViewModel.onNameChanged(it)},
            placeholder = "Digite seu nome",
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        LabeledTextField(
            label = "Email",
            value = email,
            onValueChange = {cadastroScreenViewModel.onEmailChanged(it)},
            placeholder = "Digite seu E-mail",
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        LabeledTextField(
            label = "Senha",
            value = password,
            onValueChange = {cadastroScreenViewModel.onPasswordChange(it)},
            placeholder = "Digite sua senha secreta",
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            isPassword = true
        )

        LabeledTextField(
            label = "Confirme sua senha",
            value = confirmPassword,
            onValueChange = {cadastroScreenViewModel.onPasswordConfirm(it)},
            placeholder = "Digite sua senha novamente",
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            isPassword = true
        )

        Spacer(modifier = Modifier.padding(top = 20.dp))

        MyButton(
            modifier = Modifier.fillMaxWidth().padding(15.dp).size(55.dp),
            text = "Criar Conta",
            onClick = {
                focusManager.clearFocus()
                cadastroScreenViewModel.cadastroUser()
            },
            fontSize = 18.sp,
            buttonColors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onSurface,
                contentColor = MaterialTheme.colorScheme.surface,
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(),
        )

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                Spacer(modifier = Modifier.padding(2.dp))
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }


    }
}