package com.seng440.jeh128.seng440assignment2.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.seng440.jeh128.seng440assignment2.R
import com.seng440.jeh128.seng440assignment2.ViewModel.ExercisesViewModel

@Composable
fun LogPBScreen(
    viewModel: ExercisesViewModel,
    navigateBack: () -> Unit,
) {
    val showCreateDialog = rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            LogPBTopBar {
                navigateBack()
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showCreateDialog.value = !showCreateDialog.value }) {
                Icon(
                    imageVector = if (showCreateDialog.value) Icons.Default.Clear else Icons.Filled.Add,
                    contentDescription = null
                )
            }
        }
    ) {
        if (showCreateDialog.value) LogPBContent()
    }

    BackHandler {
        navigateBack()
    }
}


@Composable
fun LogPBContent(
) {
    val weight = rememberSaveable { mutableStateOf("") }
    val location = rememberSaveable { mutableStateOf("") }
    val date = rememberSaveable { mutableStateOf("") }

    Card(Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = weight.value,
                onValueChange = { weight.value = it },
                label = { Text(stringResource(id = R.string.weight)) })

            OutlinedTextField(
                value = location.value,
                onValueChange = { location.value = it },
                label = { Text(stringResource(id = R.string.location)) })

            OutlinedTextField(
                value = date.value,
                onValueChange = { date.value = it },
                label = { Text(stringResource(id = R.string.date)) })

            OutlinedButton(onClick = { }) {
                Text(stringResource(id = R.string.select_video))
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