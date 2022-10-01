package com.seng440.jeh128.seng440assignment2.presentation.components

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.text.format.DateUtils
import androidx.camera.core.CameraSelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.seng440.jeh128.seng440assignment2.R
import com.seng440.jeh128.seng440assignment2.core.Constants
import kotlinx.coroutines.launch


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun VideoCapture(
    modifier: Modifier = Modifier,
    onVideoUri: (Uri) -> Unit = { },
){
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var recording: Recording? = remember { null }
    val previewView: PreviewView = remember { PreviewView(context) }
    val videoCapture: MutableState<VideoCapture<Recorder>?> = remember { mutableStateOf(null) }
    val recordingStarted: MutableState<Boolean> = remember { mutableStateOf(false) }

    val audioEnabled: MutableState<Boolean> = remember { mutableStateOf(false) }
    val cameraSelector: MutableState<CameraSelector> = remember {
        mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA)
    }

    LaunchedEffect(previewView) {
        videoCapture.value = context.createVideoCaptureUseCase(
            lifecycleOwner = lifecycleOwner,
            cameraSelector = cameraSelector.value,
            previewView = previewView
        )
    }


    @Composable
    fun CaptureVideo(

    ) {
        var durationNanos by remember { mutableStateOf(0L) }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize()
            )
            if (durationNanos > 0) {
                Timer(
                    modifier = Modifier.align(Alignment.TopCenter),
                    seconds = (durationNanos / 1000000000)
                )
            }
            IconButton(
                onClick = {
                    if (!recordingStarted.value) {
                        videoCapture.value?.let { videoCapture ->
                            recordingStarted.value = true

                            recording = startRecordingVideo(
                                context = context,
                                filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                                videoCapture = videoCapture,
                                executor = context.mainExecutor,
                                audioEnabled = audioEnabled.value
                            ) { videoRecordEvent ->
                                if (videoRecordEvent is VideoRecordEvent.Finalize) {
                                    val uri = videoRecordEvent.outputResults.outputUri
                                    onVideoUri(uri)
                                }
                                val recordingStats = videoRecordEvent.recordingStats
                                durationNanos = recordingStats.recordedDurationNanos
                            }
                        }
                    } else {
                        recordingStarted.value = false
                        recording?.stop()
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
            ) {
                Icon(
                    painter = painterResource(if (recordingStarted.value) R.drawable.ic_stop else R.drawable.ic_record),
                    contentDescription = "",
                    modifier = Modifier.size(64.dp)
                )
            }
            if (!recordingStarted.value) {
                IconButton(
                    onClick = {
                        audioEnabled.value = !audioEnabled.value
                    },
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 32.dp)
                ) {
                    Icon(
                        painter = painterResource(if (audioEnabled.value) R.drawable.ic_mic_on else R.drawable.ic_mic_off),
                        contentDescription = "",
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
            if (!recordingStarted.value) {
                IconButton(
                    onClick = {
                        cameraSelector.value =
                            if (cameraSelector.value == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
                            else CameraSelector.DEFAULT_BACK_CAMERA
                        lifecycleOwner.lifecycleScope.launch {
                            videoCapture.value = context.createVideoCaptureUseCase(
                                lifecycleOwner = lifecycleOwner,
                                cameraSelector = cameraSelector.value,
                                previewView = previewView
                            )
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 32.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_switch_camera),
                        contentDescription = "",
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
        }
    }



    Permission(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        ),
        rationale = stringResource(id = R.string.permission_rationale_camera_and_audio),
        permissionNotAvailableContent = {
            Column(modifier) {
                Text(stringResource(id = R.string.camera_and_audio_permission_not_given))
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
                            onVideoUri(Constants.EMPTY_URI)
                        }
                    ) {
                        Text(stringResource(id = R.string.cancel))
                    }
                }
            }
        },
    ) {
        CaptureVideo()
    }
}


@Composable
fun Timer(modifier: Modifier = Modifier, seconds: Long) {
    if (seconds > 0) {
        Box(modifier = Modifier
            .padding(vertical = 24.dp)
            .then(modifier)) {
            Text(
                text = DateUtils.formatElapsedTime(seconds),
                color = Color.White,
                modifier = Modifier
                    .background(color = Color.Red)
                    .padding(horizontal = 10.dp)
                    .then(modifier)
            )
        }

    }
}