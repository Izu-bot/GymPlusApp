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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gym.screens.cadastro.CadastroScreen
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
                            CadastroScreen()
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
                startDestination = "login"
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
                    CadastroScreen()
                }
            }
        }
    }
}