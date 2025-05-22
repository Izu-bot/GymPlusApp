package com.example.gym.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.gym.data.PreferencesManager
import com.example.gym.screens.cadastro.CadastroScreen
import com.example.gym.screens.cadastro.CadastroScreenViewModel
import com.example.gym.screens.home.HomeScreen
import com.example.gym.screens.home.HomeScreenViewModel
import com.example.gym.screens.login.LoginScreen
import com.example.gym.screens.login.LoginScreenViewModel
import com.example.gym.screens.login.TimerScreen
import com.example.gym.screens.medicao.MedicaoScreen
import com.example.gym.screens.medicao.MedicaoScreenViewModel
import com.example.gym.screens.progresso.PhotosScreen
import com.example.gym.screens.progresso.PhotosScreenViewModel
import com.example.gym.screens.workout.CreateWorkoutScreen
import com.example.gym.screens.workout.WorkoutScreen
import com.example.gym.screens.workout.WorkoutScreenViewModel
import com.example.gym.service.RetrofitFactory
import com.example.gym.service.api.ApiService
import kotlinx.coroutines.delay

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destination.HOME.route,
        modifier = modifier
    ) {
        composable(Destination.LOADING.route) {

            val tokenFlow = PreferencesManager.getUserToken()
            val tokenState = tokenFlow.collectAsState(initial = null)

            val apiCheck: ApiService = remember {
                RetrofitFactory().apiCheck()
            }

            LaunchedEffect(tokenState.value) {
                delay(1000)

                val isApiOk = try {
                    apiCheck.ping().isSuccessful
                } catch (e: Exception) {
                    false
                }

                val target = if (isApiOk && tokenState.value != null) {
                    Destination.HOME.route
                } else {
                    Destination.LOGIN.route
                }

                navController.navigate(target) {
                    popUpTo(Destination.LOADING.route) { inclusive = true }
                }
            }

            TimerScreen(modifier = Modifier.fillMaxSize())
        }
        composable(Destination.LOGIN.route) {
            val viewModel: LoginScreenViewModel = viewModel()
            LoginScreen(
                modifier = Modifier.fillMaxSize(),
                loginScreenViewModel = viewModel,
                navController = navController
            )
        }
        composable(Destination.CADASTRO.route) {
            val viewModel: CadastroScreenViewModel = viewModel()
            CadastroScreen(
                modifier = Modifier.fillMaxSize(),
                cadastroScreenViewModel = viewModel,
                navController = navController
            )
        }
        composable(Destination.HOME.route) {
            val viewModel: HomeScreenViewModel = viewModel()
            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                homeScreenViewModel = viewModel,
                navController = navController
            )
        }
        composable(Destination.WORKOUT.route) {
            val viewModel: WorkoutScreenViewModel = viewModel()
            WorkoutScreen(
                modifier = Modifier.fillMaxSize(),
                workoutScreenViewModel = viewModel,
                navController = navController
            )
        }
        composable(Destination.PHOTOS.route) {
            val viewModel: PhotosScreenViewModel = viewModel()
            PhotosScreen(
                modifier = Modifier.fillMaxSize(),
                photosScreenViewModel = viewModel,
                navController = navController
            )
        }
        composable(Destination.MEDICAO.route) {
            val viewModel: MedicaoScreenViewModel = viewModel()
            MedicaoScreen(
                modifier = Modifier.fillMaxSize(),
                medicaoScreenViewModel = viewModel,
                navController = navController
            )
        }

        // Navegação independente (não aparece no BottomBar)
        navigation(
            route = "independent", startDestination = Destination.HOME.route
        ) {
            composable(Destination.CREATE_WORKUT.route) {
                CreateWorkoutScreen(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController
                )
            }
        }
    }
}
