package com.seng440.jeh128.seng440assignment2.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.compose.rememberNavController
import com.seng440.jeh128.seng440assignment2.core.NotificationService
import com.seng440.jeh128.seng440assignment2.navigation.NavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        WindowCompat.setDecorFitsSystemWindows(window, false)
        val sharePreferences: SharedPreferences =
            getSharedPreferences("Preference_data", Context.MODE_PRIVATE)

        setContent {
            NavGraph(
                navController = rememberNavController(),
                sharedPreferences = sharePreferences,
            )
        }
    }
}