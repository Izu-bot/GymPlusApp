package com.example.gym.screens.login

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gym.R
import com.example.gym.components.LabeledTextField
import com.example.gym.components.MyButton
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(modifier: Modifier = Modifier, loginScreenViewModel: LoginScreenViewModel, navController: NavController) {

    val email by loginScreenViewModel.emailLogin.observeAsState(initial = "")
    val senha by loginScreenViewModel.senhaLogin.observeAsState(initial = "")
    val errorMessage by loginScreenViewModel.errorMessage.observeAsState(initial = null)

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current

    val isLoading by loginScreenViewModel.isLoading.observeAsState(initial = false)


    // GEMINI: Observador para os eventos de Navegação e Status
    LaunchedEffect(key1 = Unit) { // Ou use uma key que reinicie se necessário
        loginScreenViewModel.navigationAndStatusEvent.observeForever { event ->
            // Tenta consumir o evento
            event.getContentIfNotHandled()?.let { navigationEvent ->
                // Processa o tipo específico de evento
                when (navigationEvent) {
                    is NavigationEvent.NavigateToHome -> {
                        // Lógica de navegação
                        val token = navigationEvent.token // Pega o token se precisar
                        Log.d("LoginScreen", "Navegando para Home. Token: $token")
                        navController.navigate("home") {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        }
                    }
                    is NavigationEvent.ShowStatusMessage -> {
                        // Lógica para mostrar Snackbar/Toast
                        val message = navigationEvent.message
                        scope.launch {
                            snackbarHostState.showSnackbar(message)
                        }
                    }
                }
            }
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
        Column(
            modifier
            .fillMaxSize()
            .padding(paddingValues)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(top = 110.dp))
            Icon(
                painter = painterResource(id = R.drawable.exercise_150dp),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "Icon",
            )
            Spacer(modifier = Modifier.padding(top = 15.dp))
            Text(
                text = "Gym+",
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(top = 35.dp))
            LabeledTextField(
                label = "Email",
                value = email,
                onValueChange = { loginScreenViewModel.onEmailChanged(it) },
                placeholder = "Digite seu E-mail",
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                isError = errorMessage != null,
                supportingText = {
                    if (errorMessage != null) {
                        Text(
                            text = errorMessage ?: "",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            LabeledTextField(
                label = "Senha",
                value = senha,
                onValueChange = { loginScreenViewModel.onSenhaChanged(it) },
                placeholder = "Digite sua senha secreta",
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                isPassword = true,
                keyboardActions = KeyboardActions {
                    if (!isLoading) loginScreenViewModel.performLogin()
                },
                enabled = !isLoading,
                isError = errorMessage != null,
                supportingText = {
                    if (errorMessage != null) {
                        Text(
                            text = errorMessage ?: "",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
            MyButton(
                text = "Entrar",
                fontSize = 18.sp,
                onClick = {
                    focusManager.clearFocus()
                    loginScreenViewModel.performLogin()
//                navController.navigate("home")
                },
                buttonColors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onSurface,
                    contentColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(),
                modifier = Modifier.fillMaxWidth().padding(15.dp).size(55.dp)
            )

            if (isLoading) {
                Spacer(modifier = Modifier.padding(2.dp))
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.padding(2.dp))
            }

            Row {
                Text(
                    text = "Não possui uma conta?",
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = "Criar uma conta",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate("cadastro")
                    })
            }

        }
    }
}