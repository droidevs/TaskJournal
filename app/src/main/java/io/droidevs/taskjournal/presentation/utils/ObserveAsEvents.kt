package io.droidevs.taskjournal.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow

/**
 * Collects [flow] only while the surrounding lifecycle is at least [minActiveState],
 * invoking [onEvent] for each emission. Intended for one-shot ViewModel event flows
 * (navigation, snackbars) as opposed to persisted UI state, which should be collected
 * with `collectAsState` instead.
 */
@Composable
fun <T> ObserveAsEvents(
    flow: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    onEvent: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val currentOnEvent by rememberUpdatedState(onEvent)

    androidx.compose.runtime.LaunchedEffect(flow, lifecycleOwner.lifecycle, key1, key2) {
        flow.flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState)
            .collect { event -> currentOnEvent(event) }
    }
}