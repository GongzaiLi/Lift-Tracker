package com.seng440.jeh128.seng440assignment2.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.seng440.jeh128.seng440assignment2.R
import com.seng440.jeh128.seng440assignment2.ViewModel.ExercisesViewModel
import com.seng440.jeh128.seng440assignment2.navigation.Screen
import com.seng440.jeh128.seng440assignment2.presentation.components.ThemeType
import com.seng440.jeh128.seng440assignment2.presentation.components.getIconFromDrawable

@Composable
fun PreferenceScreen(
    viewModel: ExercisesViewModel,
    navController: NavController,
    darkMode: MutableState<Boolean>,
    themeType: MutableState<ThemeType>,
) {
    Scaffold(
        topBar = {
            PreferenceTopBar(navigateBack = { navController.navigate(Screen.MainScreen.route) })
        }
    ) {
        Column {
            ToggleThemeAlertDialog(darkMode)
        }

    }
}

@Composable
fun PreferenceTopBar(navigateBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.preference_name)
            )
        },
        navigationIcon = {
            IconButton(
                onClick = navigateBack
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
    )
}

@Composable
fun ToggleThemeAlertDialog(
    darkMode: MutableState<Boolean>,
) {

    val openDialog = remember { mutableStateOf(false) }

    PreferenceCard(stringResource(id = R.string.theme), "ic_baseline_color_lens_24") {
        openDialog.value = true
    }

    if (openDialog.value) {

        AlertDialog(onDismissRequest = {
            openDialog.value = false
        }, title = {
//            Text(
//                text = "Choose Theme", // todo update
//                style = MaterialTheme.typography.h2
//            )
            DarkModeCheckBox(darkMode)

        }, text = {

        }, confirmButton = {
            TextButton(onClick = {
                openDialog.value = false
            }) {
                Text(
                    text = stringResource(id = R.string.add)
                )
            }
        }, dismissButton = {
            TextButton(
                onClick = {
                    openDialog.value = false
                }
            ) {
                Text(
                    text = stringResource(id = R.string.dismiss)
                )
            }
        })
    }
}


@Composable
fun PreferenceCard(functionName: String, iconName: String, onClick: () -> Unit) {

    Card (
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(15.dp)
            .clip(RoundedCornerShape(10.dp))
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
    ) {
        Row(            modifier = Modifier
            .fillMaxWidth()
            .padding(
                all = 12.dp
            ),
            verticalAlignment = Alignment.CenterVertically,) {
            Icon(
                painter = painterResource(id = getIconFromDrawable(iconName = iconName)),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.padding(40.dp))
            Text(
                text = functionName,
                style = MaterialTheme.typography.h2
            )
        }

    }
}

@Composable
fun DarkModeCheckBox(darkMode: MutableState<Boolean>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = stringResource(R.string.dark_mode))
        // Set mode
        // Select Theme
        Checkbox(
            checked = darkMode.value,
            onCheckedChange = { checked -> darkMode.value = checked },
        )

    }
}

