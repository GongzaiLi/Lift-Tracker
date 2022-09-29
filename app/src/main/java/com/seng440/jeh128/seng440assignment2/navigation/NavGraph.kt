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
import com.seng440.jeh128.seng440assignment2.navigation.Screen.MainScreen
import com.seng440.jeh128.seng440assignment2.navigation.Screen.ViewExerciseScreen
import com.seng440.jeh128.seng440assignment2.navigation.Screen.PreferenceScreen
import com.seng440.jeh128.seng440assignment2.presentation.ExercisesListScreen
import com.seng440.jeh128.seng440assignment2.presentation.PreferenceScreen
import com.seng440.jeh128.seng440assignment2.presentation.ViewExerciseScreen
import com.seng440.jeh128.seng440assignment2.presentation.components.ThemeType
import com.seng440.jeh128.seng440assignment2.ui.theme.BlueTheme
import com.seng440.jeh128.seng440assignment2.ui.theme.PinkTheme
import com.seng440.jeh128.seng440assignment2.ui.theme.PurpleTheme
import com.seng440.jeh128.seng440assignment2.ui.theme.YellowTheme

@Composable
fun NavGraph(
    navController: NavHostController,
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
                    navController = navController
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

        }
    }

}

