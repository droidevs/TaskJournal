package io.droidevs.taskjournal.presentation.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

/**
 * Thin wrapper over [combinedClickable] so list item composables don't need to import
 * `ExperimentalFoundationApi` and build an interaction source themselves at every call site.
 * Uses [LocalIndication] rather than the Material ripple artifact directly, since this module
 * only depends on `material3`.
 */
@OptIn(ExperimentalFoundationApi::class)
fun Modifier.combinedClickableCompat(
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null
): Modifier = composed {
    val interactionSource = remember { MutableInteractionSource() }
    this.combinedClickable(
        interactionSource = interactionSource,
        indication = LocalIndication.current,
        onLongClick = onLongClick,
        onClick = onClick
    )
}