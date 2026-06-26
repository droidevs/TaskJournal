package io.droidevs.taskjournal.presentation.ui.snackbar

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * Describes an optional retry/undo action attached to a [SnackBarEvent].
 */
data class SnackBarAction(
    val name: String,
    val onAction: () -> Unit
)

/**
 * A single Snackbar request: a message and an optional action.
 */
data class SnackBarEvent(
    val message: String,
    val action: SnackBarAction? = null
)

/**
 * Process-wide channel for requesting Snackbars from anywhere (ViewModels, screen builders)
 * without needing a `SnackbarHostState` reference. The root Scaffold collects this exactly once.
 */
object SnackBarController {

    private val _events = Channel<SnackBarEvent>()
    val events = _events.receiveAsFlow()

    suspend fun sendEvent(event: SnackBarEvent) {
        _events.send(event)
    }
}