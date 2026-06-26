package io.droidevs.taskjournal.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.droidevs.taskjournal.data.preference.AutoLockMinutesDataStore
import io.droidevs.taskjournal.data.preference.AutoSaveDraftsDataStore
import io.droidevs.taskjournal.data.preference.BackupEnabledDataStore
import io.droidevs.taskjournal.data.preference.BackupLocationDataStore
import io.droidevs.taskjournal.data.preference.BackupOnlyOnWifiDataStore
import io.droidevs.taskjournal.data.preference.BiometricLockEnabledDataStore
import io.droidevs.taskjournal.data.preference.CalendarViewModeDataStore
import io.droidevs.taskjournal.data.preference.CompactModeDataStore
import io.droidevs.taskjournal.data.preference.ConfirmBeforeDeleteDataStore
import io.droidevs.taskjournal.data.preference.DateFormatPatternDataStore
import io.droidevs.taskjournal.data.preference.DefaultCategoryDataStore
import io.droidevs.taskjournal.data.preference.DefaultPriorityDataStore
import io.droidevs.taskjournal.data.preference.DynamicColorDataStore
import io.droidevs.taskjournal.data.preference.KeepDeletedDaysDataStore
import io.droidevs.taskjournal.data.preference.MaxEndCountDataStore
import io.droidevs.taskjournal.data.preference.MaxFrequencyDataStore
import io.droidevs.taskjournal.data.preference.NoteListLayoutDataStore
import io.droidevs.taskjournal.data.preference.NotificationsEnabledDataStore
import io.droidevs.taskjournal.data.preference.ReminderLeadMinutesDataStore
import io.droidevs.taskjournal.data.preference.ReminderSoundEnabledDataStore
import io.droidevs.taskjournal.data.preference.ReminderVibrationEnabledDataStore
import io.droidevs.taskjournal.data.preference.ShowArchivedDataStore
import io.droidevs.taskjournal.data.preference.ShowCompletedDataStore
import io.droidevs.taskjournal.data.preference.ShowPinnedFirstDataStore
import io.droidevs.taskjournal.data.preference.ShowWordCountDataStore
import io.droidevs.taskjournal.data.preference.SortOrderDataStore
import io.droidevs.taskjournal.data.preference.StartScreenDataStore
import io.droidevs.taskjournal.data.preference.SyncEnabledDataStore
import io.droidevs.taskjournal.data.preference.ThemeModeDataStore
import io.droidevs.taskjournal.data.preference.Use24HourTimeDataStore
import io.droidevs.taskjournal.data.preference.UseAmoledBlackDataStore
import io.droidevs.taskjournal.data.preference.WeekStartDataStore
import io.droidevs.taskjournal.domain.preference.AutoLockMinutesPreference
import io.droidevs.taskjournal.domain.preference.AutoSaveDraftsPreference
import io.droidevs.taskjournal.domain.preference.BackupEnabledPreference
import io.droidevs.taskjournal.domain.preference.BackupLocationPreference
import io.droidevs.taskjournal.domain.preference.BackupOnlyOnWifiPreference
import io.droidevs.taskjournal.domain.preference.BiometricLockEnabledPreference
import io.droidevs.taskjournal.domain.preference.CalendarViewModePreference
import io.droidevs.taskjournal.domain.preference.CompactModePreference
import io.droidevs.taskjournal.domain.preference.ConfirmBeforeDeletePreference
import io.droidevs.taskjournal.domain.preference.DateFormatPatternPreference
import io.droidevs.taskjournal.domain.preference.DefaultCategoryPreference
import io.droidevs.taskjournal.domain.preference.DefaultPriorityPreference
import io.droidevs.taskjournal.domain.preference.DynamicColorPreference
import io.droidevs.taskjournal.domain.preference.KeepDeletedDaysPreference
import io.droidevs.taskjournal.domain.preference.MaxEndCountPreference
import io.droidevs.taskjournal.domain.preference.MaxFrequencyPreference
import io.droidevs.taskjournal.domain.preference.NoteListLayoutPreference
import io.droidevs.taskjournal.domain.preference.NotificationsEnabledPreference
import io.droidevs.taskjournal.domain.preference.ReminderLeadMinutesPreference
import io.droidevs.taskjournal.domain.preference.ReminderSoundEnabledPreference
import io.droidevs.taskjournal.domain.preference.ReminderVibrationEnabledPreference
import io.droidevs.taskjournal.domain.preference.ShowArchivedPreference
import io.droidevs.taskjournal.domain.preference.ShowCompletedPreference
import io.droidevs.taskjournal.domain.preference.ShowPinnedFirstPreference
import io.droidevs.taskjournal.domain.preference.ShowWordCountPreference
import io.droidevs.taskjournal.domain.preference.SortOrderPreference
import io.droidevs.taskjournal.domain.preference.StartScreenPreference
import io.droidevs.taskjournal.domain.preference.SyncEnabledPreference
import io.droidevs.taskjournal.domain.preference.ThemeModePreference
import io.droidevs.taskjournal.domain.preference.Use24HourTimePreference
import io.droidevs.taskjournal.domain.preference.UseAmoledBlackPreference
import io.droidevs.taskjournal.domain.preference.WeekStartPreference
import javax.inject.Singleton

/**
 * Binds each narrow [io.droidevs.taskjournal.domain.preference.Preference] interface to its
 * single-purpose DataStore-backed implementation. Using [Binds] (not [dagger.Provides]) since
 * each implementation has an `@Inject` constructor and this is a pure interface binding.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class PreferenceModule {

    @Binds
    @Singleton
    abstract fun bindThemeModePreference(impl: ThemeModeDataStore): ThemeModePreference

    @Binds
    @Singleton
    abstract fun bindDynamicColorPreference(impl: DynamicColorDataStore): DynamicColorPreference

    @Binds
    @Singleton
    abstract fun bindUseAmoledBlackPreference(impl: UseAmoledBlackDataStore): UseAmoledBlackPreference

    @Binds
    @Singleton
    abstract fun bindStartScreenPreference(impl: StartScreenDataStore): StartScreenPreference

    @Binds
    @Singleton
    abstract fun bindNoteListLayoutPreference(impl: NoteListLayoutDataStore): NoteListLayoutPreference

    @Binds
    @Singleton
    abstract fun bindCalendarViewModePreference(impl: CalendarViewModeDataStore): CalendarViewModePreference

    @Binds
    @Singleton
    abstract fun bindSortOrderPreference(impl: SortOrderDataStore): SortOrderPreference

    @Binds
    @Singleton
    abstract fun bindShowCompletedPreference(impl: ShowCompletedDataStore): ShowCompletedPreference

    @Binds
    @Singleton
    abstract fun bindShowArchivedPreference(impl: ShowArchivedDataStore): ShowArchivedPreference

    @Binds
    @Singleton
    abstract fun bindShowPinnedFirstPreference(impl: ShowPinnedFirstDataStore): ShowPinnedFirstPreference

    @Binds
    @Singleton
    abstract fun bindCompactModePreference(impl: CompactModeDataStore): CompactModePreference

    @Binds
    @Singleton
    abstract fun bindShowWordCountPreference(impl: ShowWordCountDataStore): ShowWordCountPreference

    @Binds
    @Singleton
    abstract fun bindAutoSaveDraftsPreference(impl: AutoSaveDraftsDataStore): AutoSaveDraftsPreference

    @Binds
    @Singleton
    abstract fun bindConfirmBeforeDeletePreference(impl: ConfirmBeforeDeleteDataStore): ConfirmBeforeDeletePreference

    @Binds
    @Singleton
    abstract fun bindKeepDeletedDaysPreference(impl: KeepDeletedDaysDataStore): KeepDeletedDaysPreference

    @Binds
    @Singleton
    abstract fun bindDefaultCategoryPreference(impl: DefaultCategoryDataStore): DefaultCategoryPreference

    @Binds
    @Singleton
    abstract fun bindDefaultPriorityPreference(impl: DefaultPriorityDataStore): DefaultPriorityPreference

    @Binds
    @Singleton
    abstract fun bindNotificationsEnabledPreference(impl: NotificationsEnabledDataStore): NotificationsEnabledPreference

    @Binds
    @Singleton
    abstract fun bindReminderLeadMinutesPreference(impl: ReminderLeadMinutesDataStore): ReminderLeadMinutesPreference

    @Binds
    @Singleton
    abstract fun bindReminderSoundEnabledPreference(impl: ReminderSoundEnabledDataStore): ReminderSoundEnabledPreference

    @Binds
    @Singleton
    abstract fun bindReminderVibrationEnabledPreference(
        impl: ReminderVibrationEnabledDataStore
    ): ReminderVibrationEnabledPreference

    @Binds
    @Singleton
    abstract fun bindBiometricLockEnabledPreference(impl: BiometricLockEnabledDataStore): BiometricLockEnabledPreference

    @Binds
    @Singleton
    abstract fun bindAutoLockMinutesPreference(impl: AutoLockMinutesDataStore): AutoLockMinutesPreference

    @Binds
    @Singleton
    abstract fun bindBackupEnabledPreference(impl: BackupEnabledDataStore): BackupEnabledPreference

    @Binds
    @Singleton
    abstract fun bindBackupOnlyOnWifiPreference(impl: BackupOnlyOnWifiDataStore): BackupOnlyOnWifiPreference

    @Binds
    @Singleton
    abstract fun bindBackupLocationPreference(impl: BackupLocationDataStore): BackupLocationPreference

    @Binds
    @Singleton
    abstract fun bindSyncEnabledPreference(impl: SyncEnabledDataStore): SyncEnabledPreference

    @Binds
    @Singleton
    abstract fun bindWeekStartPreference(impl: WeekStartDataStore): WeekStartPreference

    @Binds
    @Singleton
    abstract fun bindUse24HourTimePreference(impl: Use24HourTimeDataStore): Use24HourTimePreference

    @Binds
    @Singleton
    abstract fun bindDateFormatPatternPreference(impl: DateFormatPatternDataStore): DateFormatPatternPreference

    @Binds
    @Singleton
    abstract fun bindMaxFrequencyPreference(impl: MaxFrequencyDataStore): MaxFrequencyPreference

    @Binds
    @Singleton
    abstract fun bindMaxEndCountPreference(impl: MaxEndCountDataStore): MaxEndCountPreference
}