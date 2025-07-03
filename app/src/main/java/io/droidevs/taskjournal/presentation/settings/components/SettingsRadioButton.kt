package io.droidevs.taskjournal.presentation.settings.components

import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import io.droidevs.taskjournal.presentation.settings.LocalSettingsGroupEnabled
import io.droidevs.taskjournal.presentation.settings.SettingsTileColors
import io.droidevs.taskjournal.presentation.settings.SettingsTileDefaults
import io.droidevs.taskjournal.presentation.settings.SettingsTileScaffold

@Composable
fun SettingsRadioButton(
    state: Boolean,
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = LocalSettingsGroupEnabled.current,
    icon: @Composable (() -> Unit)? = null,
    subtitle: @Composable (() -> Unit)? = null,
    colors: SettingsTileColors = SettingsTileDefaults.colors(),
    checkboxColors: RadioButtonColors =
        RadioButtonDefaults.colors(
            selectedColor = colors.actionColor(enabled),
            disabledSelectedColor = colors.actionColor(enabled),
        ),
    tonalElevation: Dp = SettingsTileDefaults.Elevation,
    shadowElevation: Dp = SettingsTileDefaults.Elevation,
    semanticProperties: (SemanticsPropertyReceiver.() -> Unit) = {},
    onClick: () -> Unit,
) {
    SettingsTileScaffold(
        modifier =
            Modifier.toggleable(
                enabled = enabled,
                value = state,
                role = Role.RadioButton,
                onValueChange = { onClick() },
            ).semantics(properties = semanticProperties).then(modifier),
        enabled = enabled,
        title = title,
        subtitle = subtitle,
        icon = icon,
        colors = colors,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
    ) {
        RadioButton(
            modifier = Modifier.clearAndSetSemantics { },
            enabled = enabled,
            selected = state,
            onClick = onClick,
            colors = checkboxColors,
        )
    }
}