package com.seng440.jeh128.seng440assignment2.presentation.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.*
import com.seng440.jeh128.seng440assignment2.R

@ExperimentalPermissionsApi
@Composable
fun Permission(
    permissions: List<String>,
    rationale: String,
    permissionNotAvailableContent: @Composable () -> Unit = { },
    content: @Composable () -> Unit = { }
) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = permissions
    )
    PermissionsRequired(
        multiplePermissionsState = permissionState,
        permissionsNotGrantedContent = {
            Rationale(
                text = rationale,
                onRequestPermission = { permissionState.launchMultiplePermissionRequest() }
            )
        },
        permissionsNotAvailableContent = permissionNotAvailableContent,
        content = content
    )

}

@Composable
private fun Rationale(
    text: String,
    onRequestPermission: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { /* Don't */ },
        title = {
            Text(text = stringResource(id = R.string.permission_request))
        },
        text = {
            Text(text)
        },
        confirmButton = {
            Button(onClick = onRequestPermission) {
                Text(stringResource(id = R.string.ok))
            }
        }
    )
}