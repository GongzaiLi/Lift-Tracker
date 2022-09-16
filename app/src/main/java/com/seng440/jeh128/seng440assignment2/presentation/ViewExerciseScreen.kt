package com.seng440.jeh128.seng440assignment2.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.seng440.jeh128.seng440assignment2.ViewModel.ExercisesViewModel
import com.seng440.jeh128.seng440assignment2.domain.model.Exercise
import java.time.format.DateTimeFormatter

@Composable
fun ViewExerciseScreen(
    viewModel: ExercisesViewModel,
    exerciseId: Int,
    navigateBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getExercise(exerciseId)
    }
    Scaffold(
        topBar = {
            ViewExerciseTopBar(
                navigateBack = navigateBack,
                exercise = viewModel.exercise,
            )
        },
        content = { padding ->
            ViewExerciseContent(
                padding,
                exercise = viewModel.exercise,
            )
        },
    )
}

@Composable
fun ViewExerciseTopBar(
    navigateBack: () -> Unit,
    exercise: Exercise,
) {
    TopAppBar (
        title = {
            Text(
                text = exercise.name
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

@Composable
fun ViewExerciseContent(
    padding: PaddingValues,
    exercise: Exercise,
) {
    Column(
        modifier = Modifier
            .padding(30.dp, 10.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = exercise.name,
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.h1,
        )
    }
}