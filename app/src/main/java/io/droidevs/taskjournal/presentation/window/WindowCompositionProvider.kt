package io.droidevs.onlinelibrary.ui.window

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp
import io.droidevs.taskjournal.presentation.window.WindowSize


val  LocalWindow = compositionLocalOf {
    WindowInfo(
        windowSize = WindowSize(0.dp, 0.dp),
        foldableInfo = null
    )
}