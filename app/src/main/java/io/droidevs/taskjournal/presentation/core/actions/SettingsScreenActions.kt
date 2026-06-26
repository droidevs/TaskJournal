package io.droidevs.taskjournal.presentation.core.actions

import io.droidevs.taskjournal.domain.model.CalendarViewMode
import io.droidevs.taskjournal.domain.model.NoteListLayout
import io.droidevs.taskjournal.domain.model.NotePriority
import io.droidevs.taskjournal.domain.model.SortOrder
import io.droidevs.taskjournal.domain.model.StartScreen
import io.droidevs.taskjournal.domain.model.ThemeMode
import io.droidevs.taskjournal.domain.model.WeekStart

sealed interface SettingsScreenAction {

    // Appearance
    data class SetThemeMode(val mode: ThemeMode) : SettingsScreenAction
    data class SetDynamicColor(val enabled: Boolean) : SettingsScreenAction
    data class SetUseAmoledBlack(val enabled: Boolean) : SettingsScreenAction
    data class SetStartScreen(val screen: StartScreen) : SettingsScreenAction
    data class SetNoteListLayout(val layout: NoteListLayout) : SettingsScreenAction
    data class SetCalendarViewMode(val mode: CalendarViewMode) : SettingsScreenAction

    // Notes behavior
    data class SetSortOrder(val order: SortOrder) : SettingsScreenAction
    data class SetShowCompleted(val show: Boolean) : SettingsScreenAction
    data class SetShowArchived(val show: Boolean) : SettingsScreenAction
    data class SetShowPinnedFirst(val show: Boolean) : SettingsScreenAction
    data class SetCompactMode(val enabled: Boolean) : SettingsScreenAction
    data class SetShowWordCount(val show: Boolean) : SettingsScreenAction
    data class SetAutoSaveDrafts(val enabled: Boolean) : SettingsScreenAction
    data class SetConfirmBeforeDelete(val enabled: Boolean) : SettingsScreenAction
    data class SetKeepDeletedDays(val days: Int) : SettingsScreenAction
    data class SetDefaultCategory(val categoryId: Long?) : SettingsScreenAction
    data class SetDefaultPriority(val priority: NotePriority) : SettingsScreenAction

    // System: notifications, reminders, security, backup, sync
    data class SetNotificationsEnabled(val enabled: Boolean) : SettingsScreenAction
    data class SetReminderLeadMinutes(val minutes: Int) : SettingsScreenAction
    data class SetReminderSoundEnabled(val enabled: Boolean) : SettingsScreenAction
    data class SetReminderVibrationEnabled(val enabled: Boolean) : SettingsScreenAction
    data class SetBiometricLockEnabled(val enabled: Boolean) : SettingsScreenAction
    data class SetAutoLockMinutes(val minutes: Int) : SettingsScreenAction
    data class SetBackupEnabled(val enabled: Boolean) : SettingsScreenAction
    data class SetBackupOnlyOnWifi(val enabled: Boolean) : SettingsScreenAction
    data class SetBackupLocation(val location: String) : SettingsScreenAction
    data class SetSyncEnabled(val enabled: Boolean) : SettingsScreenAction

    // Date & time / recurrence limits
    data class SetWeekStart(val weekStart: WeekStart) : SettingsScreenAction
    data class SetUse24HourTime(val use24Hour: Boolean) : SettingsScreenAction
    data class SetDateFormatPattern(val pattern: String) : SettingsScreenAction
    data class SetMaxFrequency(val value: Int) : SettingsScreenAction
    data class SetMaxEndCount(val value: Int) : SettingsScreenAction

    data object NavigateBack : SettingsScreenAction
}