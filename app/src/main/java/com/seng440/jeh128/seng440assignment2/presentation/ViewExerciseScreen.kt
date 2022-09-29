package com.seng440.jeh128.seng440assignment2.presentation

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.seng440.jeh128.seng440assignment2.ViewModel.ExercisesViewModel
import com.seng440.jeh128.seng440assignment2.domain.model.Exercise
import com.seng440.jeh128.seng440assignment2.domain.model.ExerciseWithPersonalBests
import com.seng440.jeh128.seng440assignment2.domain.model.PersonalBest
import java.time.LocalDateTime
import com.seng440.jeh128.seng440assignment2.R
import java.time.format.DateTimeFormatter

@Composable
fun ViewExerciseScreen(
    viewModel: ExercisesViewModel,
    exerciseId: Int,
    navigateToLogPBScreen: () -> Unit,
    navigateBack: () -> Unit
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
        floatingActionButton = {
            LogPBFloatingActionButton(
                logPbClicked = { navigateToLogPBScreen() }
            )
        }
    )
}

@Composable
fun LogPBFloatingActionButton(
    logPbClicked: () -> Unit,
) {
    ExtendedFloatingActionButton(
        onClick = logPbClicked,
        backgroundColor = MaterialTheme.colors.primary,
        icon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.log_pb),
            )
        },
        text = {
            Text(
                stringResource(id = R.string.log_pb),
            )
        },
    )
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
    exerciseWithPersonalBests: ExerciseWithPersonalBests,
    viewModel: ExercisesViewModel
) {
    Column(
        modifier = Modifier
            .padding(30.dp, 10.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            itemsIndexed(
                items = exerciseWithPersonalBests.personalBests.reversed()
            ) { index, personalBest ->
                PersonalBestCard(
                    is_current_PB = index == 0,
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
    is_current_PB: Boolean
) {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd LLLL yyyy")

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
            .fillMaxWidth()
    ) {
        Row(
            modifier = if (is_current_PB) {
                Modifier
                    .fillMaxWidth()
                    .padding(
                        all = 12.dp
                    ).background(Color.Yellow)
            } else {
                Modifier
                    .fillMaxWidth()
                    .padding(
                        all = 12.dp
                    ).background(Color.White)
            },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(
                        fraction = 0.90f
                    )
            ){
                Text(
                    text = stringResource(id = R.string.pb_weight, personalBest.pbWeight.toString()),
                    style = MaterialTheme.typography.h2,
                )
                Spacer(
                    modifier = Modifier.height(2.dp)
                )
                Text(
                    text = stringResource(id = R.string.pb_date, personalBest.pbDate.format(formatter)),
                    style = MaterialTheme.typography.h3,
                )
                Spacer(
                    modifier = Modifier.height(2.dp)
                )
                Text(
                    text = stringResource(id = R.string.pb_location, personalBest.pbLocation),
                    style = MaterialTheme.typography.h3,
                )
            }
        }
    }
}