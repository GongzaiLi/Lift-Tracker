package com.seng440.jeh128.seng440assignment2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.seng440.jeh128.seng440assignment2.ViewModel.ExercisesViewModel
import com.seng440.jeh128.seng440assignment2.core.NotificationService
import com.seng440.jeh128.seng440assignment2.navigation.Screen.*
import com.seng440.jeh128.seng440assignment2.presentation.*
import com.seng440.jeh128.seng440assignment2.presentation.components.ThemeType
import com.seng440.jeh128.seng440assignment2.ui.theme.BlueTheme
import com.seng440.jeh128.seng440assignment2.ui.theme.PinkTheme
import com.seng440.jeh128.seng440assignment2.ui.theme.PurpleTheme
import com.seng440.jeh128.seng440assignment2.ui.theme.YellowTheme

@Composable
fun NavGraph (
    navController: NavHostController,
    notificationService: NotificationService
) {
    val viewModel: ExercisesViewModel = hiltViewModel()

    val darkMode = remember { mutableStateOf(false) }
    val themeType = remember { mutableStateOf(ThemeType.PURPLE) }
    val themeFunction: @Composable (
        isDarkMode: Boolean, content: @Composable () -> Unit
    ) -> Unit =
        when (themeType.value) {
            ThemeType.PURPLE -> { isDarkMode, content -> PurpleTheme(isDarkMode, content) }
            ThemeType.YELLOW -> { isDarkMode, content -> YellowTheme(isDarkMode, content) }
            ThemeType.BLUE -> { isDarkMode, content -> BlueTheme(isDarkMode, content) }
            ThemeType.PINK -> { isDarkMode, content -> PinkTheme(isDarkMode, content) }
        }

    themeFunction.invoke(darkMode.value) {

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
                    },
                    navController = navController,
                    notificationService = notificationService,
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
                    navigateToLogPBScreen = { navController.navigate("${LogPBScreen.route}/${exerciseId}") },
                    navigateBack = {
                        navController.popBackStack()
                    },
                    navigateToVideoPlayerScreen = { personalBestId ->
                        navController.navigate("${VideoPlayerScreen.route}/${personalBestId}")
                    }
                )
            }

            composable(
                route = "${LogPBScreen.route}/{exerciseId}",
                arguments = listOf(
                    navArgument("exerciseId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val exerciseId = backStackEntry.arguments?.getInt("exerciseId") ?: 0
                LogPBScreen(
                    viewModel = viewModel,
                    navigateBack = {
                        navController.popBackStack()
                    },
                    exerciseId = exerciseId,
                )
            }
            composable(
                route = PreferenceScreen.route
            ) {
                PreferenceScreen(
                    viewModel = viewModel,
                    navController = navController,
                    themeType = themeType,
                    darkMode = darkMode
                )
            }

            composable(
                route = "${VideoPlayerScreen.route}/{personalBestId}",
                arguments = listOf(
                    navArgument("personalBestId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val personalBestId = backStackEntry.arguments?.getInt("personalBestId") ?: 0
                VideoPlayerScreen(
                    personalBestId = personalBestId,
                    navigateBack = {
                        navController.popBackStack()
                    },
                    viewModel = viewModel
                )
            }

        }
    }

}

