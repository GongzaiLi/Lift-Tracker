package com.seng440.jeh128.seng440assignment2.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seng440.jeh128.seng440assignment2.ViewModel.ExercisesViewModel
import com.seng440.jeh128.seng440assignment2.domain.model.Exercise

@Composable
fun ViewExerciseScreen(
    viewModel: ExercisesViewModel,
    exerciseId: Int,
    navigateToLogPBScreen: () -> Unit,
    navigateToRecordPBScreen: () -> Unit,
    navigateBack: () -> Unit
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
        floatingActionButton = {
            TestFab(
                logPbClicked = { navigateToLogPBScreen() },
                recordPbClicked = { navigateToRecordPBScreen() }
            )
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun TestFab(
    logPbClicked: () -> Unit = {},
    recordPbClicked: () -> Unit = {}
) {
    val expanded = rememberSaveable { mutableStateOf(false) }

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
        AnimatedVisibility(
            visible = expanded.value,
            enter = fadeIn(),
            exit = fadeOut()
        ) {

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    Modifier
                        .padding(vertical = 8.dp)
                        .clickable {
                            logPbClicked()
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(modifier = Modifier.padding(horizontal = 2.dp), text = "Log PB")
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .background(Color.Green, CircleShape),
                        imageVector = Icons.Default.List,
                        contentDescription = null
                    )

                }

                Row(
                    Modifier
                        .padding(vertical = 8.dp)
                        .clickable {
                            recordPbClicked()
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(modifier = Modifier.padding(horizontal = 2.dp), text = "Record PB")
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .background(Color.Green, CircleShape),
                        imageVector = Icons.Default.List,
                        contentDescription = null
                    )

                }
            }
        }

        FloatingActionButton(onClick = { expanded.value = !expanded.value }) {
            Icon(
                imageVector = if (expanded.value) Icons.Default.Clear else Icons.Filled.Add,
                contentDescription = null
            )
        }

    }

}

@Composable
fun ViewExerciseTopBar(
    navigateBack: () -> Unit,
    exercise: Exercise,
) {
    TopAppBar(
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