package com.seng440.jeh128.seng440assignment2.presentation

import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.shadow
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
    sharedPreferences: SharedPreferences,
) {
    val sharedPreferencesEditor = sharedPreferences.edit()

    Scaffold(
        topBar = {
            PreferenceTopBar(navigateBack = { navController.navigate(Screen.MainScreen.route) })
        }
    ) {
        Column {

            ModeSwitch(darkMode, sharedPreferencesEditor)
            ToggleThemeAlertDialog(
                stringResource(id = R.string.theme),
                "ic_baseline_color_lens_24",
                themeType.value.color
            ) { ThemeBox(themeType, sharedPreferencesEditor) }
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
    cardName: String,
    iconName: String,
    color: Color,
    title: @Composable () -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }

    PreferenceCard(cardName, iconName, color) {
        openDialog.value = true
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            }, text = {
                title()
            }, confirmButton = {})
    }
}


@Composable
fun PreferenceCard(functionName: String, iconName: String, color: Color, onClick: () -> Unit) {

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    all = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = getIconFromDrawable(iconName = iconName)),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = color,
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
fun ModeSwitch(
    darkMode: MutableState<Boolean>,
    sharedPreferencesEditor: SharedPreferences.Editor
) {
    val iconName = if (darkMode.value) "ic_baseline_dark_mode_24" else "ic_baseline_light_mode_24"
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    all = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = getIconFromDrawable(iconName = iconName)),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.padding(40.dp))
            Text(
                text = stringResource(id = R.string.mode),
                style = MaterialTheme.typography.h2
            )
            Spacer(modifier = Modifier.padding(40.dp))
            Switch(
                checked = darkMode.value,
                onCheckedChange = {
                        checked ->
                    darkMode.value = checked
                    sharedPreferencesEditor.putBoolean("dark_mode", checked)
                    sharedPreferencesEditor.commit()
                }
            )
        }
    }
}


@Composable
fun ThemeBox(
    themeType: MutableState<ThemeType>,
    sharedPreferencesEditor: SharedPreferences.Editor
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        enumValues<ThemeType>().forEach { type ->
            val color = type.color
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .shadow(15.dp, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(color)
                    .border(
                        width = 3.dp,
                        color = if (themeType.value.color == color) {
                            Color.Black
                        } else Color.Transparent,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {
                        themeType.value = type
                        sharedPreferencesEditor.putString("theme_type", type.name)
                        sharedPreferencesEditor.commit()
                    })

        }
    }

}


