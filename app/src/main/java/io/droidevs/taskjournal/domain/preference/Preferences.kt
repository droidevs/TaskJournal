package io.droidevs.taskjournal.domain.preference

import io.droidevs.taskjournal.domain.model.CalendarViewMode
import io.droidevs.taskjournal.domain.model.NoteListLayout
import io.droidevs.taskjournal.domain.model.NotePriority
import io.droidevs.taskjournal.domain.model.SortOrder
import io.droidevs.taskjournal.domain.model.StartScreen
import io.droidevs.taskjournal.domain.model.ThemeMode
import io.droidevs.taskjournal.domain.model.WeekStart
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow

/**
 * Common shape every single-value preference exposes.
 * Screens/use cases should depend on these narrow interfaces instead of
 * the full [AppPreferencesPreference] blob
 * whenever they only care about one setting.
 */
interface Preference<T> {
    val value: Flow<Result<T, PreferenceError>>
    suspend fun save(value: T): Result<T, PreferenceError>
}

interface ThemeModePreference : Preference<ThemeMode>

interface DynamicColorPreference : Preference<Boolean>

interface UseAmoledBlackPreference : Preference<Boolean>

interface StartScreenPreference : Preference<StartScreen>

interface NoteListLayoutPreference : Preference<NoteListLayout>

interface CalendarViewModePreference : Preference<CalendarViewMode>

interface SortOrderPreference : Preference<SortOrder>

interface ShowCompletedPreference : Preference<Boolean>

interface ShowArchivedPreference : Preference<Boolean>

interface ShowPinnedFirstPreference : Preference<Boolean>

interface CompactModePreference : Preference<Boolean>

interface ShowWordCountPreference : Preference<Boolean>

interface AutoSaveDraftsPreference : Preference<Boolean>

interface ConfirmBeforeDeletePreference : Preference<Boolean>

interface KeepDeletedDaysPreference : Preference<Int>

interface DefaultCategoryPreference : Preference<Long?>

interface DefaultPriorityPreference : Preference<NotePriority>

interface NotificationsEnabledPreference : Preference<Boolean>

interface ReminderLeadMinutesPreference : Preference<Int>

interface ReminderSoundEnabledPreference : Preference<Boolean>

interface ReminderVibrationEnabledPreference : Preference<Boolean>

interface BiometricLockEnabledPreference : Preference<Boolean>

interface AutoLockMinutesPreference : Preference<Int>

interface BackupEnabledPreference : Preference<Boolean>

interface BackupOnlyOnWifiPreference : Preference<Boolean>

interface BackupLocationPreference : Preference<String>

interface SyncEnabledPreference : Preference<Boolean>

interface WeekStartPreference : Preference<WeekStart>

interface Use24HourTimePreference : Preference<Boolean>

interface DateFormatPatternPreference : Preference<String>

interface MaxFrequencyPreference : Preference<Int>

interface MaxEndCountPreference : Preference<Int>