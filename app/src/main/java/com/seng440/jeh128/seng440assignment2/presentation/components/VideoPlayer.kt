package com.seng440.jeh128.seng440assignment2.presentation.components

import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun VideoPlayer(
    uri: Uri
) {
    val context = LocalContext.current
    var playWhenReady by rememberSaveable { mutableStateOf(true) }
    var startPositionMs by rememberSaveable { mutableStateOf(0L) }
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(uri), startPositionMs)
            repeatMode = ExoPlayer.REPEAT_MODE_ALL
            playWhenReady = playWhenReady
            prepare()
            play()
        }
    }
    DisposableEffect(
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                StyledPlayerView(context).apply {
                    player = exoPlayer
                    useController = true

                    FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            }
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }

    val mainHandler = Handler(Looper.getMainLooper())

    mainHandler.post(object : Runnable {
        override fun run() {
            startPositionMs = exoPlayer.currentPosition
            mainHandler.postDelayed(this, 100)
        }
    })

}