package com.example.gym.screens.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gym.R
import com.example.gym.components.MyButton
import com.example.gym.service.AuthService
import com.example.gym.service.RetrofitFactory

@Composable
fun LoginScreen(modifier: Modifier = Modifier, loginScreenViewModel: LoginScreenViewModel, navController: NavController) {

    val email by loginScreenViewModel.emailLoing.observeAsState(initial = "")
    val senha by loginScreenViewModel.senhaLogin.observeAsState(initial = "")
    val senhaVisivel by loginScreenViewModel.senhaVisivel.observeAsState(initial = false)
    val icon = if (senhaVisivel) R.drawable.visibility else R.drawable.visibility_off
    val focusManager = LocalFocusManager.current

    val isLoading by loginScreenViewModel.isLoading.observeAsState(initial = false)


    LaunchedEffect(key1 = Unit) {
        loginScreenViewModel.loginSuccess.observeForever { event ->
            println("Login bem-sucedido via Composable.")

            navController.navigate("home") {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }

    Column(modifier
        .fillMaxSize()
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
        OutlinedTextField(
            value = email,
            onValueChange = {loginScreenViewModel.onEmailChanged(it)},
            placeholder = {
                Text(text = "exemplo@email.com",
                    color = MaterialTheme.colorScheme.primary
                )
            },
            textStyle = TextStyle(fontSize = 18.sp),
            shape = RoundedCornerShape(14.dp),
            maxLines = 1,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth().padding(15.dp)
        )
        OutlinedTextField(
            value = senha,
            onValueChange = {loginScreenViewModel.onSenhaChanged(it)},
            placeholder = {
                Text(text = "senha12",
                    color = MaterialTheme.colorScheme.primary
                    )
            },
            textStyle = TextStyle(fontSize = 18.sp),
            shape = RoundedCornerShape(14.dp),
            maxLines = 1,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface
            ),
            visualTransformation = if (senhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = {loginScreenViewModel.toggleSenhaVisivel()} ) {
                    Icon(
                        contentDescription = "Icon",
                        painter = painterResource(id = icon),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth().padding(15.dp),
            keyboardActions = KeyboardActions {
                if (!isLoading) loginScreenViewModel.performLogin()
            },
            enabled = !isLoading
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
                containerColor = MaterialTheme.colorScheme.onTertiaryContainer,
                contentColor = MaterialTheme.colorScheme.tertiaryContainer
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(),
            modifier = Modifier.fillMaxWidth().padding(15.dp).size(55.dp)
        )

        if (isLoading) {
            Spacer(modifier = Modifier.padding(2.dp))
            CircularProgressIndicator()
            Spacer(modifier = Modifier.padding(2.dp))
        }

        Row {
            Text(
                text = "Não possui uma conta?",
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.padding(2.dp))
            Text(
                text = "Criar Conta",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable{
                navController.navigate("cadastro")
            })
        }

    }

}