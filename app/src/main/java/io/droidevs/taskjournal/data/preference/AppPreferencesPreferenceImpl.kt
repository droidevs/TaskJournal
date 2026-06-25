package io.droidevs.taskjournal.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import io.droidevs.taskjournal.data.preference.exceptions.flowCatchingPreference
import io.droidevs.taskjournal.data.preference.exceptions.runCatchingPreference
import io.droidevs.taskjournal.domain.model.AppPreferences
import io.droidevs.taskjournal.domain.model.CalendarViewMode
import io.droidevs.taskjournal.domain.model.NoteListLayout
import io.droidevs.taskjournal.domain.model.NotePriority
import io.droidevs.taskjournal.domain.model.SortOrder
import io.droidevs.taskjournal.domain.model.StartScreen
import io.droidevs.taskjournal.domain.model.ThemeMode
import io.droidevs.taskjournal.domain.model.WeekStart
import io.droidevs.taskjournal.domain.preference.AppPreferencesPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferencesPreferenceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : AppPreferencesPreference {

    private object Keys {
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val DYNAMIC_COLOR = booleanPreferencesKey("dynamic_color")
        val USE_AMOLED_BLACK = booleanPreferencesKey("use_amoled_black")
        val LANGUAGE_TAG = stringPreferencesKey("language_tag")
        val START_SCREEN = stringPreferencesKey("start_screen")
        val NOTE_LIST_LAYOUT = stringPreferencesKey("note_list_layout")
        val CALENDAR_VIEW_MODE = stringPreferencesKey("calendar_view_mode")
        val SORT_ORDER = stringPreferencesKey("sort_order")
        val SHOW_COMPLETED = booleanPreferencesKey("show_completed")
        val SHOW_ARCHIVED = booleanPreferencesKey("show_archived")
        val SHOW_PINNED_FIRST = booleanPreferencesKey("show_pinned_first")
        val COMPACT_MODE = booleanPreferencesKey("compact_mode")
        val SHOW_WORD_COUNT = booleanPreferencesKey("show_word_count")
        val AUTO_SAVE_DRAFTS = booleanPreferencesKey("auto_save_drafts")
        val CONFIRM_BEFORE_DELETE = booleanPreferencesKey("confirm_before_delete")
        val KEEP_DELETED_DAYS = intPreferencesKey("keep_deleted_days")
        val DEFAULT_CATEGORY_ID = longPreferencesKey("default_category_id")
        val DEFAULT_PRIORITY = stringPreferencesKey("default_priority")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        val REMINDER_LEAD_MINUTES = intPreferencesKey("reminder_lead_minutes")
        val REMINDER_SOUND_ENABLED = booleanPreferencesKey("reminder_sound_enabled")
        val REMINDER_VIBRATION_ENABLED = booleanPreferencesKey("reminder_vibration_enabled")
        val BIOMETRIC_LOCK_ENABLED = booleanPreferencesKey("biometric_lock_enabled")
        val AUTO_LOCK_MINUTES = intPreferencesKey("auto_lock_minutes")
        val BACKUP_ENABLED = booleanPreferencesKey("backup_enabled")
        val BACKUP_ONLY_ON_WIFI = booleanPreferencesKey("backup_only_on_wifi")
        val BACKUP_LOCATION = stringPreferencesKey("backup_location")
        val SYNC_ENABLED = booleanPreferencesKey("sync_enabled")
        val WEEK_STARTS_ON = stringPreferencesKey("week_starts_on")
        val USE_24_HOUR_TIME = booleanPreferencesKey("use_24_hour_time")
        val DATE_FORMAT_PATTERN = stringPreferencesKey("date_format_pattern")
        val MAX_FREQUENCY = intPreferencesKey("max_frequency")
        val MAX_END_COUNT = intPreferencesKey("max_end_count")
    }

    override fun getPreferences(): Flow<Result<AppPreferences, PreferenceError>> {
        return flowCatchingPreference {
            dataStore.data.map { preferences -> preferences.toAppPreferences() }
        }
    }

    override suspend fun setPreferences(preferences: AppPreferences): Result<Unit, PreferenceError> {
        return runCatchingPreference {
            dataStore.edit { mutablePreferences -> mutablePreferences.write(preferences) }
            Unit
        }
    }

    override suspend fun updatePreferences(
        transform: (AppPreferences) -> AppPreferences
    ): Result<Unit, PreferenceError> {
        return runCatchingPreference {
            val current = dataStore.data.first().toAppPreferences()
            dataStore.edit { mutablePreferences -> mutablePreferences.write(transform(current)) }
            Unit
        }
    }

    private fun Preferences.toAppPreferences(): AppPreferences {
        val defaults = AppPreferences()
        return AppPreferences(
            themeMode = enumPreference(this[Keys.THEME_MODE], defaults.themeMode),
            dynamicColor = this[Keys.DYNAMIC_COLOR] ?: defaults.dynamicColor,
            useAmoledBlack = this[Keys.USE_AMOLED_BLACK] ?: defaults.useAmoledBlack,
            languageTag = this[Keys.LANGUAGE_TAG] ?: defaults.languageTag,
            startScreen = enumPreference(this[Keys.START_SCREEN], defaults.startScreen),
            noteListLayout = enumPreference(this[Keys.NOTE_LIST_LAYOUT], defaults.noteListLayout),
            calendarViewMode = enumPreference(this[Keys.CALENDAR_VIEW_MODE], defaults.calendarViewMode),
            sortOrder = enumPreference(this[Keys.SORT_ORDER], defaults.sortOrder),
            showCompleted = this[Keys.SHOW_COMPLETED] ?: defaults.showCompleted,
            showArchived = this[Keys.SHOW_ARCHIVED] ?: defaults.showArchived,
            showPinnedFirst = this[Keys.SHOW_PINNED_FIRST] ?: defaults.showPinnedFirst,
            compactMode = this[Keys.COMPACT_MODE] ?: defaults.compactMode,
            showWordCount = this[Keys.SHOW_WORD_COUNT] ?: defaults.showWordCount,
            autoSaveDrafts = this[Keys.AUTO_SAVE_DRAFTS] ?: defaults.autoSaveDrafts,
            confirmBeforeDelete = this[Keys.CONFIRM_BEFORE_DELETE] ?: defaults.confirmBeforeDelete,
            keepDeletedDays = this[Keys.KEEP_DELETED_DAYS] ?: defaults.keepDeletedDays,
            defaultCategoryId = this[Keys.DEFAULT_CATEGORY_ID],
            defaultPriority = enumPreference(this[Keys.DEFAULT_PRIORITY], defaults.defaultPriority),
            notificationsEnabled = this[Keys.NOTIFICATIONS_ENABLED] ?: defaults.notificationsEnabled,
            reminderLeadMinutes = this[Keys.REMINDER_LEAD_MINUTES] ?: defaults.reminderLeadMinutes,
            reminderSoundEnabled = this[Keys.REMINDER_SOUND_ENABLED] ?: defaults.reminderSoundEnabled,
            reminderVibrationEnabled = this[Keys.REMINDER_VIBRATION_ENABLED] ?: defaults.reminderVibrationEnabled,
            biometricLockEnabled = this[Keys.BIOMETRIC_LOCK_ENABLED] ?: defaults.biometricLockEnabled,
            autoLockMinutes = this[Keys.AUTO_LOCK_MINUTES] ?: defaults.autoLockMinutes,
            backupEnabled = this[Keys.BACKUP_ENABLED] ?: defaults.backupEnabled,
            backupOnlyOnWifi = this[Keys.BACKUP_ONLY_ON_WIFI] ?: defaults.backupOnlyOnWifi,
            backupLocation = this[Keys.BACKUP_LOCATION] ?: defaults.backupLocation,
            syncEnabled = this[Keys.SYNC_ENABLED] ?: defaults.syncEnabled,
            weekStartsOn = enumPreference(this[Keys.WEEK_STARTS_ON], defaults.weekStartsOn),
            use24HourTime = this[Keys.USE_24_HOUR_TIME] ?: defaults.use24HourTime,
            dateFormatPattern = this[Keys.DATE_FORMAT_PATTERN] ?: defaults.dateFormatPattern,
            maxFrequency = this[Keys.MAX_FREQUENCY] ?: defaults.maxFrequency,
            maxEndCount = this[Keys.MAX_END_COUNT] ?: defaults.maxEndCount
        )
    }

    private fun MutablePreferences.write(preferences: AppPreferences) {
        this[Keys.THEME_MODE] = preferences.themeMode.name
        this[Keys.DYNAMIC_COLOR] = preferences.dynamicColor
        this[Keys.USE_AMOLED_BLACK] = preferences.useAmoledBlack
        this[Keys.LANGUAGE_TAG] = preferences.languageTag
        this[Keys.START_SCREEN] = preferences.startScreen.name
        this[Keys.NOTE_LIST_LAYOUT] = preferences.noteListLayout.name
        this[Keys.CALENDAR_VIEW_MODE] = preferences.calendarViewMode.name
        this[Keys.SORT_ORDER] = preferences.sortOrder.name
        this[Keys.SHOW_COMPLETED] = preferences.showCompleted
        this[Keys.SHOW_ARCHIVED] = preferences.showArchived
        this[Keys.SHOW_PINNED_FIRST] = preferences.showPinnedFirst
        this[Keys.COMPACT_MODE] = preferences.compactMode
        this[Keys.SHOW_WORD_COUNT] = preferences.showWordCount
        this[Keys.AUTO_SAVE_DRAFTS] = preferences.autoSaveDrafts
        this[Keys.CONFIRM_BEFORE_DELETE] = preferences.confirmBeforeDelete
        this[Keys.KEEP_DELETED_DAYS] = preferences.keepDeletedDays
        preferences.defaultCategoryId?.let { this[Keys.DEFAULT_CATEGORY_ID] = it } ?: remove(Keys.DEFAULT_CATEGORY_ID)
        this[Keys.DEFAULT_PRIORITY] = preferences.defaultPriority.name
        this[Keys.NOTIFICATIONS_ENABLED] = preferences.notificationsEnabled
        this[Keys.REMINDER_LEAD_MINUTES] = preferences.reminderLeadMinutes
        this[Keys.REMINDER_SOUND_ENABLED] = preferences.reminderSoundEnabled
        this[Keys.REMINDER_VIBRATION_ENABLED] = preferences.reminderVibrationEnabled
        this[Keys.BIOMETRIC_LOCK_ENABLED] = preferences.biometricLockEnabled
        this[Keys.AUTO_LOCK_MINUTES] = preferences.autoLockMinutes
        this[Keys.BACKUP_ENABLED] = preferences.backupEnabled
        this[Keys.BACKUP_ONLY_ON_WIFI] = preferences.backupOnlyOnWifi
        this[Keys.BACKUP_LOCATION] = preferences.backupLocation
        this[Keys.SYNC_ENABLED] = preferences.syncEnabled
        this[Keys.WEEK_STARTS_ON] = preferences.weekStartsOn.name
        this[Keys.USE_24_HOUR_TIME] = preferences.use24HourTime
        this[Keys.DATE_FORMAT_PATTERN] = preferences.dateFormatPattern
        this[Keys.MAX_FREQUENCY] = preferences.maxFrequency
        this[Keys.MAX_END_COUNT] = preferences.maxEndCount
    }

    private inline fun <reified T : Enum<T>> enumPreference(value: String?, defaultValue: T): T {
        return value?.let { runCatching { enumValueOf<T>(it) }.getOrDefault(defaultValue) } ?: defaultValue
    }
}
