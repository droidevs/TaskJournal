package io.droidevs.taskjournal.presentation.settings.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.Dp
import io.droidevs.taskjournal.presentation.settings.LocalSettingsGroupEnabled
import io.droidevs.taskjournal.presentation.settings.SettingsTileColors
import io.droidevs.taskjournal.presentation.settings.SettingsTileDefaults
import io.droidevs.taskjournal.presentation.settings.SettingsTileScaffold

@Composable
fun SettingsTriStateCheckbox(
    state: Boolean?,
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = LocalSettingsGroupEnabled.current,
    icon: @Composable (() -> Unit)? = null,
    subtitle: @Composable (() -> Unit)? = null,
    colors: SettingsTileColors = SettingsTileDefaults.colors(),
    checkboxColors: CheckboxColors =
        CheckboxDefaults.colors(
            checkedColor = colors.actionColor(enabled),
            checkmarkColor = contentColorFor(colors.actionColor(enabled)),
            disabledCheckedColor = colors.actionColor(enabled),
        ),
    tonalElevation: Dp = SettingsTileDefaults.Elevation,
    shadowElevation: Dp = SettingsTileDefaults.Elevation,
    semanticProperties: (SemanticsPropertyReceiver.() -> Unit) = {},
    onCheckedChange: (Boolean) -> Unit = {},
) {
    val update: () -> Unit = { onCheckedChange(state?.not() ?: true) }
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .triStateToggleable(
                    state = mapNullableBooleanToToggleableState(state),
                    onClick = update,
                    enabled = enabled,
                    role = Role.Checkbox,
                ).semantics(properties = semanticProperties).then(modifier),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SettingsTileScaffold(
            enabled = enabled,
            title = title,
            subtitle = subtitle,
            icon = icon,
            colors = colors,
            tonalElevation = tonalElevation,
            shadowElevation = shadowElevation,
        ) {
            TriStateCheckbox(
                modifier = Modifier.clearAndSetSemantics { },
                enabled = enabled,
                state = mapNullableBooleanToToggleableState(state),
                onClick = update,
                colors = checkboxColors,
            )
        }
    }
}

private fun mapNullableBooleanToToggleableState(state: Boolean?) =
    when (state) {
        true -> ToggleableState.On
        false -> ToggleableState.Off
        null -> ToggleableState.Indeterminate
    }