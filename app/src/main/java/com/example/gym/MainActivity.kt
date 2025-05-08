package com.example.gym

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gym.data.PreferencesManager
import com.example.gym.screens.TimerScreen
import com.example.gym.screens.cadastro.CadastroScreen
import com.example.gym.screens.cadastro.CadastroScreenViewModel
import com.example.gym.screens.home.HomeScreen
import com.example.gym.screens.home.HomeScreenViewModel
import com.example.gym.screens.login.LoginScreen
import com.example.gym.screens.login.LoginScreenViewModel
import com.example.gym.ui.theme.GymTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferencesManager.init(this)
        enableEdgeToEdge()
        setContent {
            GymTheme {
                val navController = rememberNavController()
                var startDestination by remember { mutableStateOf("loading") }
                val tokenFlow = PreferencesManager.getUserToken()
                val tokenState = tokenFlow.collectAsState(initial = null)

                // Controle de navegação baseado no token
                LaunchedEffect(tokenState.value) {
                    delay(1000)
                    startDestination = when (tokenState.value) {
                        null -> "login"
                        else -> "home"
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    if (startDestination == "loading") {
                        TimerScreen(
                            modifier = Modifier.padding(innerPadding))
                    } else {
                        NavHost(
                            navController = navController,
                            startDestination = startDestination
                        ) {
                            composable(route = "login") {
                                val viewModel: LoginScreenViewModel = viewModel()
                                LoginScreen(
                                    modifier = Modifier.padding(innerPadding),
                                    loginScreenViewModel = viewModel,
                                    navController = navController
                                )
                            }
                            composable(route = "cadastro") {
                                val viewModel: CadastroScreenViewModel = viewModel()
                                CadastroScreen(
                                    modifier = Modifier.padding(innerPadding),
                                    cadastroScreenViewModel = viewModel,
                                    navController = navController
                                )
                            }
                            composable(route = "home") {
                                val viewModel: HomeScreenViewModel = viewModel()
                                HomeScreen(
                                    modifier = Modifier.padding(innerPadding),
                                    homeScreenViewModel = viewModel,
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}