package com.seng440.jeh128.seng440assignment2.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.seng440.jeh128.seng440assignment2.R
import com.seng440.jeh128.seng440assignment2.ViewModel.ExercisesViewModel
import com.seng440.jeh128.seng440assignment2.domain.model.Exercise
import com.seng440.jeh128.seng440assignment2.domain.model.ExerciseWithPersonalBests
import com.seng440.jeh128.seng440assignment2.domain.model.PersonalBest
import com.seng440.jeh128.seng440assignment2.presentation.components.ShareButton
import java.time.format.DateTimeFormatter

@Composable
fun ViewExerciseScreen(
    viewModel: ExercisesViewModel,
    exerciseId: Int,
    navigateToLogPBScreen: () -> Unit,
    navigateToVideoPlayerScreen: (personalBestId: Int) -> Unit,
    navigateBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getExercise(exerciseId)
        viewModel.getExerciseWithPersonalBests(exerciseId)
    }
    val weightUnit = viewModel.weighUnit

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
                weightUnit = weightUnit,
                viewModel = viewModel,
                navigateToVideoPlayerScreen = navigateToVideoPlayerScreen
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ViewExerciseContent(
    padding: PaddingValues,
    exercise: Exercise,
    exerciseWithPersonalBests: ExerciseWithPersonalBests,
    weightUnit: WeightUnit,
    viewModel: ExercisesViewModel,
    navigateToVideoPlayerScreen: (personalBestId: Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp, 10.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        val personalBestList =
            remember(exerciseWithPersonalBests) { exerciseWithPersonalBests.personalBests.reversed() }
        val topPersonalBest = personalBestList.sortedBy { it.pbWeight }.reversed().firstOrNull()
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {

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
                Divider(Modifier.padding(4.dp))
            }

            ShareButton(
                title = stringResource(R.string.myPersonalbest),
                subject = stringResource(R.string.myPersonalbest),
                text = stringResource(
                    R.string.personal_best_share_text, exercise.name,
                    topPersonalBest?.pbWeight.toString(), topPersonalBest?.pbLocation.toString()
                )
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            itemsIndexed(
                items = personalBestList,
                key = { _, personalBest ->
                    personalBest.personalBestId
                }
            ) { index, personalBest ->

                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart) {
                            viewModel.deletePersonalBest(personalBest)
                        }
                        true
                    }
                )

                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    background = {

                        val color by animateColorAsState(
                            targetValue = when (dismissState.targetValue) {
                                DismissValue.Default -> MaterialTheme.colors.background
                                else -> Color.Red
                            }
                        )

                        val icons = Icons.Default.Delete

                        val scale by animateFloatAsState(
                            targetValue =
                            if (dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(10.dp))
                                .background(color)
                                .padding(start = 12.dp, end = 12.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(icons, contentDescription = null, modifier = Modifier.scale(scale))
                        }
                    },
                    dismissContent = {
                        PersonalBestCard(
                            is_current_PB = index == 0,
                            personalBest = personalBest,
                            weightUnit = weightUnit,
                            navigateToVideoPlayerScreen = navigateToVideoPlayerScreen
                        )

                    }
                )
            }
        }
    }


}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PersonalBestCard(
    personalBest: PersonalBest,
    is_current_PB: Boolean,
    weightUnit: WeightUnit,
    navigateToVideoPlayerScreen: (personalBestId: Int) -> Unit,
) {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd LLLL yyyy")
    val weight: String
    val unit: String
    if (weightUnit == WeightUnit.KILOGRAMS) {
        weight = personalBest.pbWeight.toString()
        unit = stringResource(R.string.kg_unit)
    } else {
        weight = personalBest.pbWeight.toPounds().toString() //convert
        unit = stringResource(R.string.pounds_unit)
    }

    val modifier = if (is_current_PB)
        Modifier.background(Color.Yellow)
        else Modifier



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
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(
                        fraction = 0.90f
                    )
            ) {
                Text(
                    text = stringResource(
                        id = R.string.pb_weight,
                        weight,
                        unit
                    ),
                    style = MaterialTheme.typography.h2,
                )
                Spacer(
                    modifier = Modifier.height(2.dp)
                )
                Text(
                    text = stringResource(
                        id = R.string.pb_date,
                        personalBest.pbDate.format(formatter)
                    ),
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
            IconButton(
                onClick = {
                    navigateToVideoPlayerScreen(personalBest.personalBestId)
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.PlayArrow,
                    contentDescription = null,
                )
            }
        }
    }
}

fun Double.toPounds(): Double = this.times(2.2046)
