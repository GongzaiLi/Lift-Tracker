package com.seng440.jeh128.seng440assignment2.navigation


import android.content.SharedPreferences
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.seng440.jeh128.seng440assignment2.ViewModel.ExercisesViewModel
import com.seng440.jeh128.seng440assignment2.core.NotificationService
import com.seng440.jeh128.seng440assignment2.navigation.Screen.*
import com.seng440.jeh128.seng440assignment2.presentation.*
import com.seng440.jeh128.seng440assignment2.presentation.components.ThemeType
import com.seng440.jeh128.seng440assignment2.ui.theme.*


@Composable
fun NavGraph(
    navController: NavHostController,
    sharedPreferences: SharedPreferences,
) {
    val viewModel: ExercisesViewModel = hiltViewModel()

    val darkModeValue: Boolean = sharedPreferences.getBoolean("dark_mode", false)
    val themeTypeValue: String? = sharedPreferences.getString("theme_type", "PURPLE")
    val textStyle: ThemeType = ThemeType.valueOf(themeTypeValue ?: "PURPLE")

    val darkMode = remember { mutableStateOf(darkModeValue) }
    val themeType = remember { mutableStateOf(textStyle) }

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
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(MaterialTheme.colors.primaryVariant)

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
                    darkMode = darkMode,
                    sharedPreferences = sharedPreferences,
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

