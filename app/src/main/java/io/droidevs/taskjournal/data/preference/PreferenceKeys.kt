package io.droidevs.taskjournal.data.preference

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * Single source of truth for every DataStore [androidx.datastore.preferences.core.Preferences.Key].
 * Keeping these centralized avoids key-name collisions across the many small preference classes.
 */
internal object PreferenceKeys {
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