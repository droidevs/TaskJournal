package io.droidevs.taskjournal.presentation.core.events

sealed interface SettingsScreenEvent {

    data object NavigateBack : SettingsScreenEvent

    data object SettingSavedSuccessfully : SettingsScreenEvent

    data object SettingSaveFailed : SettingsScreenEvent
}