package com.seng440.jeh128.seng440assignment2.presentation.components

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.seng440.jeh128.seng440assignment2.core.Constants.Companion.EMPTY_URI
import com.seng440.jeh128.seng440assignment2.R

@ExperimentalPermissionsApi
@Composable
fun GallerySelect(
    modifier: Modifier = Modifier,
    onImageUri: (Uri) -> Unit = { },
    ) {
    var uri2: Uri = EMPTY_URI
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri: Uri? ->
            onImageUri(uri ?: EMPTY_URI)
            if (uri != null) {
                uri2 = uri
            }
            context.contentResolver.takePersistableUriPermission(uri2,
                Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
    )

    @Composable
    fun LaunchGallery() {
        SideEffect {
            launcher.launch(arrayOf("video/*"))
        }
    }

    Permission(
        permission = Manifest.permission.ACCESS_MEDIA_LOCATION,
        rationale = stringResource(id = R.string.permission_rationale_gallery),
        permissionNotAvailableContent = {
            Column(modifier) {
                Text(stringResource(id = R.string.gallery_permission_not_given))
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Button(
                        modifier = Modifier.padding(4.dp),
                        onClick = {
                            context.startActivity(
                                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", context.packageName, null)
                                }
                            )
                        }
                    ) {
                        Text(stringResource(id = R.string.open_setting))
                    }
                    // If they don't want to grant permissions, this button will result in going back
                    Button(
                        modifier = Modifier.padding(4.dp),
                        onClick = {
                            onImageUri(EMPTY_URI)
                        }
                    ) {
                        Text(stringResource(id = R.string.cancel))
                    }
                }
            }
        },
    ) {
        LaunchGallery()
    }
}