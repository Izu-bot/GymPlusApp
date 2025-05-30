package com.example.gym.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.gym.data.PreferencesManager
import com.example.gym.model.planilha.SpreadsheetResponse
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
import com.example.gym.screens.workout.CreateSpreadsheetScreen
import com.example.gym.screens.workout.CreateWorkoutScreen
import com.example.gym.screens.workout.DetailSpreadsheetScreen
import com.example.gym.screens.workout.WorkoutScreen
import com.example.gym.screens.workout.WorkoutScreenViewModel
import com.example.gym.service.RetrofitFactory
import com.example.gym.service.api.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.delay
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Destination.LOADING.route,
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
            composable(Destination.CREATE_SPREADSHEET.route) {
                val viewModel: WorkoutScreenViewModel = viewModel()
                CreateSpreadsheetScreen(
                    modifier = Modifier.fillMaxSize(),
                    workoutScreenViewModel = viewModel,
                    navController = navController
                )
            }
            composable(Destination.CREATE_WORKOUT.route) {
                val viewModel: WorkoutScreenViewModel = viewModel()
                CreateWorkoutScreen(
                    modifier = Modifier.fillMaxSize(),
                    createWorkoutScreenViewModel = viewModel,
                    navController = navController
                )
            }
            composable(Destination.DETAILS.route, arguments = listOf(
                navArgument("spreadsheetId") {
                    type = NavType.IntType
                }
            )) { backStackEntry ->

                val spreadsheetId = backStackEntry.arguments?.getInt("spreadsheetId") ?: -1

                val viewModel: WorkoutScreenViewModel = viewModel()

                    DetailSpreadsheetScreen(
                        modifier = Modifier.fillMaxSize(),
                        detailsSpreadsheetViewModel = viewModel,
                        navController = navController,
                        spreadsheet = spreadsheetId
                    )
            }
        }
    }
}
