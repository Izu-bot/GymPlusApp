package com.example.gym

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gym.screens.cadastro.CadastroScreen
import com.example.gym.screens.cadastro.CadastroScreenViewModel
import com.example.gym.screens.home.HomeScreen
import com.example.gym.screens.login.LoginScreen
import com.example.gym.screens.login.LoginScreenViewModel
import com.example.gym.ui.theme.GymTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GymTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        composable(route = "login") {
                            val viewModel: LoginScreenViewModel = viewModel()
                            LoginScreen(
                                modifier = Modifier.padding(innerPadding),
                                loginScreenViewModel = viewModel,
                                navController
                            )
                        }
                        composable(route = "home") {
                            HomeScreen()
                        }
                        composable(route = "cadastro") {
                            val viewModel: CadastroScreenViewModel = viewModel()
                            CadastroScreen(
                                modifier = Modifier.padding(innerPadding),
                                cadastroScreenViewModel = viewModel,
                                navController
                            )
                        }
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    GymTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "cadastro"
            ) {
                composable(route = "login") {
                    LoginScreen(
                        modifier = Modifier.padding(innerPadding),
                        LoginScreenViewModel(),
                        navController
                    )
                }
                composable(route = "home") {
                    HomeScreen()
                }
                composable(route = "cadastro") {
                    CadastroScreen(
                        modifier = Modifier.padding(innerPadding),
                        CadastroScreenViewModel(),
                        navController
                    )
                }
            }
        }
    }
}