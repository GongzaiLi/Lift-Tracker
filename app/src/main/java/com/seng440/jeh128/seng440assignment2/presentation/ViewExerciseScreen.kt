package com.seng440.jeh128.seng440assignment2.presentation

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.seng440.jeh128.seng440assignment2.domain.model.ExerciseWithPersonalBests
import com.seng440.jeh128.seng440assignment2.domain.model.PersonalBest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ViewExerciseScreen(
    viewModel: ExercisesViewModel,
    exerciseId: Int,
    navigateBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getExercise(exerciseId)
        viewModel.getExerciseWithPersonalBests(exerciseId)
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
                exerciseWithPersonalBests = viewModel.exerciseWithPersonalBests,
                viewModel = viewModel
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
    exerciseWithPersonalBests: ExerciseWithPersonalBests,
    viewModel: ExercisesViewModel
) {
    Column() {
        Text(
            text = exercise.name,
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.h1,
        )
        Text(
            text = exercise.exerciseNotes,
            textAlign = TextAlign.Left,
            style = MaterialTheme.typography.h3,
        )
        Button(
            onClick = {
                val personalBest = PersonalBest(0, exercise.exerciseId, 10.0, "Thames", LocalDateTime.now(), Uri.EMPTY)
                viewModel.addPersonalBest(personalBest)
            }
        ) {
            Text(text = "Add")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(
                items = exerciseWithPersonalBests.personalBests
            ) { personalBest ->
                PersonalBestCard(
                    personalBest = personalBest
                )
            }
        }
    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PersonalBestCard(
    personalBest: PersonalBest,
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
            .fillMaxWidth(),
        elevation = 100.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    all = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(
                        fraction = 0.90f
                    )
            ){
                Text(
                    text = personalBest.pbLocation,
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.h1
                )
            }
        }
    }
}