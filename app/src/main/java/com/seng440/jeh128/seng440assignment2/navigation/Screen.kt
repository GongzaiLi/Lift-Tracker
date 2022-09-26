package com.seng440.jeh128.seng440assignment2.navigation

sealed class Screen(val route: String) {
    object MainScreen: Screen("MainScreen")
    object ViewExerciseScreen: Screen("ViewExercise")
    object PreferenceScreen: Screen("PreferenceScreen")
}