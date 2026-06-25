package io.droidevs.taskjournal.domain.model

data class AppPreferences(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val dynamicColor: Boolean = true,
    val useAmoledBlack: Boolean = false,
    val languageTag: String = "system",
    val startScreen: StartScreen = StartScreen.HOME,
    val noteListLayout: NoteListLayout = NoteListLayout.LIST,
    val calendarViewMode: CalendarViewMode = CalendarViewMode.MONTH,
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
    val weekStartsOn: WeekStart = WeekStart.SYSTEM,
    val use24HourTime: Boolean = true,
    val dateFormatPattern: String = "system",
    val maxFrequency: Int = 99,
    val maxEndCount: Int = 999
)

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

enum class StartScreen {
    HOME,
    NOTES,
    CALENDAR,
    CATEGORIES,
    SETTINGS
}

enum class NoteListLayout {
    LIST,
    GRID,
    COMPACT
}

enum class CalendarViewMode {
    DAY,
    WEEK,
    MONTH,
    AGENDA
}

enum class SortOrder {
    CREATED_AT_ASC,
    CREATED_AT_DESC,
    UPDATED_AT_ASC,
    UPDATED_AT_DESC,
    TITLE_ASC,
    TITLE_DESC,
    PRIORITY_ASC,
    PRIORITY_DESC,
    DUE_DATE_ASC,
    DUE_DATE_DESC
}

enum class WeekStart {
    SYSTEM,
    SATURDAY,
    SUNDAY,
    MONDAY
}

