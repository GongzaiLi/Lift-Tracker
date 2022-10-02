package com.seng440.jeh128.seng440assignment2.presentation

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.seng440.jeh128.seng440assignment2.R
import com.seng440.jeh128.seng440assignment2.ViewModel.ExercisesViewModel
import com.seng440.jeh128.seng440assignment2.domain.model.Exercise
import com.seng440.jeh128.seng440assignment2.domain.model.PersonalBest
import com.seng440.jeh128.seng440assignment2.presentation.components.DateTimePicker
import com.seng440.jeh128.seng440assignment2.presentation.components.GallerySelect
import com.seng440.jeh128.seng440assignment2.presentation.components.VideoCapture
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


private fun isLocationEnabled(context: Context): Boolean {
    val gfgLocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return gfgLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            || gfgLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

fun enableLocation(context: Context) {
    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
}

@Composable
fun LogPBScreen(
    viewModel: ExercisesViewModel,
    exerciseId: Int,
    navigateBack: () -> Unit,
) {
    var useMyLocation by remember { mutableStateOf(false) }
    val myCurrentLocation = viewModel.currentLocation

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getExercise(exerciseId)
    }
    println(useMyLocation)

    LaunchedEffect(useMyLocation) {
        if (useMyLocation) {
            if (isLocationEnabled(context)) {
                viewModel.getCurrentLocation()
            } else {
                enableLocation(context)
            }
        }

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
            useMyLocation = useMyLocation,
            myCurrentLocation = myCurrentLocation,
            toggleUseMyLocation = {
                useMyLocation = !useMyLocation
            },
            locationPermissionGranted = {
//                viewModel.getCurrentLocation()
                //do something?
            },
            addPersonalBest = { personalBest ->
                viewModel.addPersonalBest(personalBest)
            }
        )
    }

    BackHandler {
        navigateBack()
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LogPBContent(
    exercise: Exercise,
    navigateBack: () -> Unit,
    useMyLocation: Boolean,
    myCurrentLocation: String,
    locationPermissionGranted: () -> Unit,
    addPersonalBest: (personalBest: PersonalBest) -> Unit,
    toggleUseMyLocation: () -> Unit
) {
    val weight = rememberSaveable { mutableStateOf("") }
    val location =
        rememberSaveable(
            useMyLocation,
            myCurrentLocation
        ) { mutableStateOf(if (useMyLocation) myCurrentLocation else "") }
    val date = rememberSaveable { mutableStateOf(LocalDateTime.now()) }
    var vidUri by rememberSaveable { mutableStateOf(Uri.EMPTY) }

    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd LLLL yyyy - hh:mm a")

    var showGallerySelect by remember { mutableStateOf(false) }
    var showRecordVideo by remember { mutableStateOf(false) }

    Card(Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                if (showGallerySelect) {
                    GallerySelect(
                        modifier = Modifier.fillMaxSize(),
                        onImageUri = { uri ->
                            showGallerySelect = false
                            vidUri = uri
                        }
                    )
                } else if (showRecordVideo) {
                    VideoCapture(
                        modifier = Modifier.fillMaxSize(),
                        onVideoUri = { uri ->
                            showRecordVideo = false
                            vidUri = uri
                        }
                    )
                } else {
                    OutlinedButton(
                        onClick = {
                            showGallerySelect = true
                            showRecordVideo = false
                        }
                    ) {
                        Text(stringResource(id = R.string.select_video))
                    }
                    OutlinedButton(
                        onClick = {
                            showGallerySelect = false
                            showRecordVideo = true
                        }
                    ) {
                        Text(stringResource(id = R.string.record_video))
                    }
                }
            }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.7f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = weight.value,
                onValueChange = { weight.value = it },
                label = { Text(stringResource(id = R.string.weight)) })

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.7f),
                value = location.value,
                onValueChange = { location.value = it },
                label = { Text(stringResource(id = R.string.location)) },
                trailingIcon = {
                    IconButton(onClick = {
                        toggleUseMyLocation()
                    }) {
                        Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                    }
                }
            )

            if (useMyLocation) {
                RequestLocationPermissions {
                    locationPermissionGranted()
                }
            }


            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.7f),
                value = date.value.format(formatter),
                onValueChange = {},
                label = {
                    Text(stringResource(id = R.string.date))
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
                    val personalBest = PersonalBest(
                        0,
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


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermissions(locationPermissionGranted: () -> Unit) {
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    if (locationPermissionsState.allPermissionsGranted) {
        Text("Note: Using current location.")
        locationPermissionGranted()
    } else {
        Column {
            val allPermissionsRevoked =
                locationPermissionsState.permissions.size ==
                        locationPermissionsState.revokedPermissions.size

            val textToShow = if (!allPermissionsRevoked) {
                // If not all the permissions are revoked, it's because the user accepted the COARSE
                // location permission, but not the FINE one.
                "Using approximate location."
            } else if (locationPermissionsState.shouldShowRationale) {
                // Both location permissions have been denied
                "Location permission is required to fill your current location. " +
                        "Please grant the fine location."
            } else {
                // First time the user sees this feature or the user doesn't want to be asked again
                "This feature requires location permission."
            }

            val buttonText = if (!allPermissionsRevoked) {
                "Allow precise location"
            } else {
                "Request permissions"
            }

            Text(text = textToShow)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { locationPermissionsState.launchMultiplePermissionRequest() }) {
                Text(buttonText)
            }
        }
    }
}
