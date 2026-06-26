package io.droidevs.taskjournal.presentation.core.state

import io.droidevs.taskjournal.domain.model.CalendarViewMode
import io.droidevs.taskjournal.domain.model.NoteListLayout
import io.droidevs.taskjournal.domain.model.NotePriority
import io.droidevs.taskjournal.domain.model.SortOrder
import io.droidevs.taskjournal.domain.model.StartScreen
import io.droidevs.taskjournal.domain.model.ThemeMode
import io.droidevs.taskjournal.domain.model.WeekStart
import io.droidevs.taskjournal.domain.result.errors.PreferenceError

data class SettingsScreenState(
    // Appearance
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val dynamicColor: Boolean = true,
    val useAmoledBlack: Boolean = false,
    val startScreen: StartScreen = StartScreen.HOME,
    val noteListLayout: NoteListLayout = NoteListLayout.LIST,
    val calendarViewMode: CalendarViewMode = CalendarViewMode.MONTH,

    // Notes behavior
    val sortOrder: SortOrder = SortOrder.CREATED_AT_DESC,
    val showCompleted: Boolean = true,
    val showArchived: Boolean = false,
    val showPinnedFirst: Boolean = true,
    val compactMode: Boolean = false,
    val showWordCount: Boolean = true,
    val autoSaveDrafts: Boolean = true,
    val confirmBeforeDelete: Boolean = true,
    val keepDeletedDays: Int = 30,
    val defaultCategoryId: Long? = null,
    val defaultPriority: NotePriority = NotePriority.NONE,

    // System
    val notificationsEnabled: Boolean = true,
    val reminderLeadMinutes: Int = 10,
    val reminderSoundEnabled: Boolean = true,
    val reminderVibrationEnabled: Boolean = true,
    val biometricLockEnabled: Boolean = false,
    val autoLockMinutes: Int = 5,
    val backupEnabled: Boolean = false,
    val backupOnlyOnWifi: Boolean = true,
    val backupLocation: String = "",
    val syncEnabled: Boolean = false,

    // Date & time / recurrence
    val weekStartsOn: WeekStart = WeekStart.SYSTEM,
    val use24HourTime: Boolean = true,
    val dateFormatPattern: String = "system",
    val maxFrequency: Int = 99,
    val maxEndCount: Int = 999,

    val isLoading: Boolean = true,
    val error: PreferenceError? = null
)