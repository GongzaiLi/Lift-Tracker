package com.seng440.jeh128.seng440assignment2.presentation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.seng440.jeh128.seng440assignment2.ViewModel.ExercisesViewModel
import com.seng440.jeh128.seng440assignment2.presentation.components.VideoPlayer

@Composable
fun VideoPlayerScreen(
    viewModel: ExercisesViewModel,
    personalBestId: Int,
    navigateBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getPersonalBest(personalBestId)
        viewModel.getExercise(viewModel.personalBest.correspondingExerciseId)
    }
    Scaffold(
        topBar = {
            VideoPlayerTopBar(
                navigateBack = navigateBack,
                exerciseName = viewModel.exercise.name
            )
        },
        content = { padding ->
            VideoPlayerContent(uri = viewModel.personalBest.videoUri)
        }
    )
}


@Composable
fun VideoPlayerContent(
    uri: Uri,
) {
    if (uri != Uri.EMPTY) {
        VideoPlayer(
            uri
        )
    } else {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize()
        )
    }

}

@Composable
fun VideoPlayerTopBar(
    navigateBack: () -> Unit,
    exerciseName: String
) {
    TopAppBar (
        title = {
            Text(
                text = exerciseName
            )
        },
        navigationIcon = {
            IconButton(
                onClick = navigateBack
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null,
                )
            }
        },
    )
}