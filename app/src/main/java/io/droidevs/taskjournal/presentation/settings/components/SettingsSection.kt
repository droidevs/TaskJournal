package io.droidevs.taskjournal.presentation.settings.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.droidevs.taskjournal.presentation.settings.LocalSettingsTileColors
import io.droidevs.taskjournal.presentation.settings.SettingsTileDefaults

@Composable
internal fun SettingsSection(
    title: String,
    enabled: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    SettingsGroup(
        contentPadding = PaddingValues(16.dp),
        enabled = enabled,
        title = { Text(text = title) },
    ) {
        ElevatedCard(
            colors =
                CardDefaults.elevatedCardColors(
                    containerColor =
                        (
                                LocalSettingsTileColors.current
                                    ?: SettingsTileDefaults.colors()
                                ).containerColor,
                ),
        ) { content() }
    }
}