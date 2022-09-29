package com.seng440.jeh128.seng440assignment2.presentation

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.seng440.jeh128.seng440assignment2.R
import com.seng440.jeh128.seng440assignment2.ViewModel.ExercisesViewModel
import com.seng440.jeh128.seng440assignment2.domain.model.Exercise
import com.seng440.jeh128.seng440assignment2.domain.model.PersonalBest
import com.seng440.jeh128.seng440assignment2.presentation.components.DateTimePicker
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun LogPBScreen(
    viewModel: ExercisesViewModel,
    exerciseId: Int,
    navigateBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.getExercise(exerciseId)
    }

    Scaffold(
        topBar = {
            LogPBTopBar {
                navigateBack()
            }
        },
    ) {
        LogPBContent(
            exercise = viewModel.exercise,
            navigateBack = navigateBack,
            addPersonalBest =  { personalBest ->
                viewModel.addPersonalBest(personalBest)
            }
        )
    }

    BackHandler {
        navigateBack()
    }
}


@Composable
fun LogPBContent(
    exercise: Exercise,
    navigateBack: () -> Unit,
    addPersonalBest: (personalBest: PersonalBest) -> Unit,
) {
    val weight = rememberSaveable { mutableStateOf("") }
    val location = rememberSaveable { mutableStateOf("") }
    val date = rememberSaveable { mutableStateOf(LocalDateTime.now()) }
    val vidUri by rememberSaveable { mutableStateOf(Uri.EMPTY) }

    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd LLLL yyyy - hh:mm a")

    Card(Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row() {
                OutlinedButton(onClick = { }) {
                    Text(stringResource(id = R.string.select_video))
                }
                OutlinedButton(onClick = { }) {
                    Text(stringResource(id = R.string.record_video))
                }
            }
            OutlinedTextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = weight.value,
                onValueChange = { weight.value = it },
                label = { Text(stringResource(id = R.string.weight)) })

            OutlinedTextField(
                value = location.value,
                onValueChange = { location.value = it },
                label = { Text(stringResource(id = R.string.location)) })

            TextField(
                value = date.value.format(formatter),
                onValueChange = {},
                label = {
                    Text (stringResource(id = R.string.date))
                },
                readOnly = true
            )
            DateTimePicker(
                onDateSelected = { localDateTime ->
                    date.value = localDateTime
                }
            )

            TextButton(
                onClick = {

                    var weightVal = weight.value.toDoubleOrNull()
                    if (weightVal == null) {
                        weightVal = 0.0
                    }

                    navigateBack()
                    val personalBest = PersonalBest(0,
                        exercise.exerciseId, weightVal, location.value,
                        date.value, vidUri
                    )
                    addPersonalBest(personalBest)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.add)
                )
            }
        }
    }
}

@Composable
fun LogPBTopBar(
    navigateBack: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.log_pb))
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