package com.seng440.jeh128.seng440assignment2.presentation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.seng440.jeh128.seng440assignment2.R
import com.seng440.jeh128.seng440assignment2.ViewModel.ExercisesViewModel
import com.seng440.jeh128.seng440assignment2.domain.model.PersonalBest
import com.seng440.jeh128.seng440assignment2.presentation.components.VideoPlayer

@Composable
fun VideoPlayerScreen(
    viewModel: ExercisesViewModel,
    personalBestId: Int,
    navigateBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getPersonalBest(personalBestId)
    }
    Scaffold(
        topBar = {
            VideoPlayerTopBar(
                navigateBack = navigateBack,
            )
        },
        content = {
            VideoPlayerContent(
                personalBest = viewModel.personalBest,
                personalBestId = personalBestId
            )
        }
    )
}


@Composable
fun VideoPlayerContent(
    personalBest: PersonalBest,
    personalBestId: Int
) {
    if (personalBest.personalBestId == personalBestId && personalBest.videoUri != Uri.EMPTY) {
        VideoPlayer(
            personalBest.videoUri
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
) {
    TopAppBar (
        title = {
            Text(
                text = stringResource(id = R.string.play_video),
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