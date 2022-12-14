package com.seng440.jeh128.seng440assignment2.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.seng440.jeh128.seng440assignment2.R
import com.seng440.jeh128.seng440assignment2.ViewModel.ExercisesViewModel
import com.seng440.jeh128.seng440assignment2.domain.model.Exercise
import com.seng440.jeh128.seng440assignment2.navigation.Screen
import com.seng440.jeh128.seng440assignment2.presentation.components.getIconFromDrawable
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExercisesListScreen(
    viewModel: ExercisesViewModel,
    navigateToViewExerciseScreen: (exerciseId: Int) -> Unit,
    navController: NavController,
) {
    LaunchedEffect(Unit) {
        viewModel.getExercises()
    }
    Scaffold(topBar = {
        ExercisesTopBar(viewModel = viewModel,
            navigateGoPreference = { navController.navigate(Screen.PreferenceScreen.route) })
    }, content = { padding ->
        ExercisesContent(
            padding = padding,
            exercises = viewModel.exercises,
            navigateToViewExerciseScreen = navigateToViewExerciseScreen,
            viewModel = viewModel
        )
        AddExerciseAlertDialog(openDialog = viewModel.openDialog, closeDialog = {
            viewModel.closeDialog()
        }, addExercise = { exercise ->
            viewModel.addExercise(exercise)
        })
    }, floatingActionButton = {
        AddExerciseFloatingActionButton(openDialog = {
            viewModel.openDialog()
        }
        )
    })
}


@Composable
fun AddExerciseFloatingActionButton(
    openDialog: () -> Unit,
) {
    ExtendedFloatingActionButton(
        onClick = openDialog,
        backgroundColor = MaterialTheme.colors.primary,
        icon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_exercise),
            )
        },
        text = {
            Text(
                stringResource(id = R.string.add_exercise),
            )
        },
    )
}

@Composable
fun AddExerciseAlertDialog(
    openDialog: Boolean, closeDialog: () -> Unit, addExercise: (exercise: Exercise) -> Unit
) {
    if (openDialog) {
        var name by rememberSaveable { mutableStateOf("") }
        var notes by rememberSaveable { mutableStateOf("") }
        val focusRequester = FocusRequester()

        AlertDialog(
            onDismissRequest = closeDialog,
            title = {
                Text(
                    text = stringResource(id = R.string.add_exercise),
                    style = MaterialTheme.typography.h2
                )
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.exercise_name)
                            )
                        },
                        modifier = Modifier
                            .focusRequester(focusRequester)
                            .padding(4.dp),
                    )
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.exercise_notes)
                            )
                        },
                        modifier = Modifier
                            .focusRequester(focusRequester)
                            .padding(4.dp),
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        closeDialog()
                        val exercise = Exercise(0, name, notes)
                        addExercise(exercise)
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.add),
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = closeDialog
                ) {
                    Text(
                        text = stringResource(id = R.string.dismiss),
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            })
    }
}

@Composable
@ExperimentalMaterialApi
fun ExercisesContent(
    padding: PaddingValues,
    exercises: List<Exercise>,
    navigateToViewExerciseScreen: (exerciseId: Int) -> Unit,
    viewModel: ExercisesViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(10.dp, 4.dp)
    ) {
        itemsIndexed(
            items = exercises,
            key = { _, listItem -> listItem.hashCode() }) { _, exercise ->

            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        viewModel.deleteExercise(exercise)
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
                    ExerciseCard(
                        exercise = exercise,
                        navigateToViewExerciseScreen = navigateToViewExerciseScreen,
                        viewModel,
                        elevation = animateDpAsState(
                            targetValue =
                            if (dismissState.dismissDirection != null) 4.dp else 0.dp
                        ).value
                    )

                }
            )


        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExerciseCard(
    exercise: Exercise,
    navigateToViewExerciseScreen: (exerciseId: Int) -> Unit,
    viewModel: ExercisesViewModel,
    elevation: Dp
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
            .fillMaxWidth(),
        onClick = {
            navigateToViewExerciseScreen(exercise.exerciseId)
        },
        elevation = elevation
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
                modifier = Modifier.fillMaxWidth(
                    fraction = 0.90f
                )
            ) {
                Text(
                    text = exercise.name,
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.h1
                )
            }
        }
    }
}


@Composable
fun ExercisesTopBar(
    viewModel: ExercisesViewModel, navigateGoPreference: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.app_name)
                )
                IconButton(
                    onClick = navigateGoPreference
                ) {
                    Icon(
                        painterResource(id = getIconFromDrawable("ic_baseline_settings_24")),
                        contentDescription = null,
                    )
                }
            }
        },
    )
}
