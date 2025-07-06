package io.droidevs.taskjournal.presentation.ui.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import io.droidevs.taskjournal.presentation.ui.settings.LocalSettingsGroupEnabled
import io.droidevs.taskjournal.presentation.ui.settings.SettingsTileColors
import io.droidevs.taskjournal.presentation.ui.settings.SettingsTileDefaults
import io.droidevs.taskjournal.presentation.ui.settings.SettingsTileScaffold

@Composable
fun SettingsMenuLink(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = LocalSettingsGroupEnabled.current,
    icon: (@Composable () -> Unit)? = null,
    subtitle: (@Composable () -> Unit)? = null,
    action: (@Composable () -> Unit)? = null,
    colors: SettingsTileColors = SettingsTileDefaults.colors(),
    tonalElevation: Dp = SettingsTileDefaults.Elevation,
    shadowElevation: Dp = SettingsTileDefaults.Elevation,
    semanticProperties: (SemanticsPropertyReceiver.() -> Unit) = {},
    onClick: () -> Unit,
) {
    SettingsTileScaffold(
        modifier =
            Modifier.clickable(
                enabled = enabled,
                onClick = onClick,
            ).semantics(properties = semanticProperties).then(modifier),
        enabled = enabled,
        title = title,
        subtitle = subtitle,
        icon = icon,
        colors = colors,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        action = action,
    )
}