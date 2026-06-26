package io.droidevs.taskjournal.domain.usecase.preference


import javax.inject.Inject

/**
 * Single entry point for the Settings screen, grouping every preference
 * sub-domain (appearance, note behavior, system/security/backup, date & time)
 * so [io.droidevs.taskjournal.presentation.viewmodels.SettingsViewModel] only
 * needs one constructor dependency.
 */
data class SettingsUseCases @Inject constructor(
    val appearance: AppearancePreferencesUseCases,
    val notes: NotePreferencesUseCases,
    val system: SystemPreferencesUseCases,
    val dateTime: DateTimePreferencesUseCases
)