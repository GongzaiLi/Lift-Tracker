package com.seng440.jeh128.seng440assignment2.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.seng440.jeh128.seng440assignment2.ViewModel.ExercisesViewModel
import com.seng440.jeh128.seng440assignment2.navigation.Screen.MainScreen
import com.seng440.jeh128.seng440assignment2.navigation.Screen.ViewExerciseScreen
import com.seng440.jeh128.seng440assignment2.presentation.ExercisesListScreen
import com.seng440.jeh128.seng440assignment2.presentation.ViewExerciseScreen
import com.seng440.jeh128.seng440assignment2.ui.theme.Seng440Assignment2Theme

@Composable
fun NavGraph (
    navController: NavHostController,
) {
    val viewModel: ExercisesViewModel = hiltViewModel()
    Seng440Assignment2Theme {
        NavHost(
            navController = navController,
            startDestination = MainScreen.route
        ) {
            composable(
                route = MainScreen.route
            ) {
                ExercisesListScreen(
                    viewModel = viewModel,
                    navigateToViewExerciseScreen = { exerciseId ->
                        navController.navigate("${ViewExerciseScreen.route}/${exerciseId}")
                    }
                )
            }
            composable(
                route = "${ViewExerciseScreen.route}/{exerciseId}",
                arguments = listOf(
                    navArgument("exerciseId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val exerciseId = backStackEntry.arguments?.getInt("exerciseId") ?: 0
                ViewExerciseScreen(
                    viewModel = viewModel,
                    exerciseId = exerciseId,
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}