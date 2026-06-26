package io.droidevs.taskjournal.domain.usecase.preference

import io.droidevs.taskjournal.domain.preference.AutoLockMinutesPreference
import io.droidevs.taskjournal.domain.preference.BackupEnabledPreference
import io.droidevs.taskjournal.domain.preference.BackupLocationPreference
import io.droidevs.taskjournal.domain.preference.BackupOnlyOnWifiPreference
import io.droidevs.taskjournal.domain.preference.BiometricLockEnabledPreference
import io.droidevs.taskjournal.domain.preference.NotificationsEnabledPreference
import io.droidevs.taskjournal.domain.preference.ReminderLeadMinutesPreference
import io.droidevs.taskjournal.domain.preference.ReminderSoundEnabledPreference
import io.droidevs.taskjournal.domain.preference.ReminderVibrationEnabledPreference
import io.droidevs.taskjournal.domain.preference.SyncEnabledPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationsEnabledUseCase @Inject constructor(private val pref: NotificationsEnabledPreference) {
    operator fun invoke(): Flow<Result<Boolean, PreferenceError>> = pref.value
}

class SetNotificationsEnabledUseCase @Inject constructor(private val pref: NotificationsEnabledPreference) {
    suspend operator fun invoke(enabled: Boolean): Result<Boolean, PreferenceError> = pref.save(enabled)
}

class GetReminderLeadMinutesUseCase @Inject constructor(private val pref: ReminderLeadMinutesPreference) {
    operator fun invoke(): Flow<Result<Int, PreferenceError>> = pref.value
}

class SetReminderLeadMinutesUseCase @Inject constructor(private val pref: ReminderLeadMinutesPreference) {
    suspend operator fun invoke(minutes: Int): Result<Int, PreferenceError> = pref.save(minutes.coerceAtLeast(0))
}

class GetReminderSoundEnabledUseCase @Inject constructor(private val pref: ReminderSoundEnabledPreference) {
    operator fun invoke(): Flow<Result<Boolean, PreferenceError>> = pref.value
}

class SetReminderSoundEnabledUseCase @Inject constructor(private val pref: ReminderSoundEnabledPreference) {
    suspend operator fun invoke(enabled: Boolean): Result<Boolean, PreferenceError> = pref.save(enabled)
}

class GetReminderVibrationEnabledUseCase @Inject constructor(private val pref: ReminderVibrationEnabledPreference) {
    operator fun invoke(): Flow<Result<Boolean, PreferenceError>> = pref.value
}

class SetReminderVibrationEnabledUseCase @Inject constructor(private val pref: ReminderVibrationEnabledPreference) {
    suspend operator fun invoke(enabled: Boolean): Result<Boolean, PreferenceError> = pref.save(enabled)
}

class GetBiometricLockEnabledUseCase @Inject constructor(private val pref: BiometricLockEnabledPreference) {
    operator fun invoke(): Flow<Result<Boolean, PreferenceError>> = pref.value
}

class SetBiometricLockEnabledUseCase @Inject constructor(private val pref: BiometricLockEnabledPreference) {
    suspend operator fun invoke(enabled: Boolean): Result<Boolean, PreferenceError> = pref.save(enabled)
}

class GetAutoLockMinutesUseCase @Inject constructor(private val pref: AutoLockMinutesPreference) {
    operator fun invoke(): Flow<Result<Int, PreferenceError>> = pref.value
}

class SetAutoLockMinutesUseCase @Inject constructor(private val pref: AutoLockMinutesPreference) {
    suspend operator fun invoke(minutes: Int): Result<Int, PreferenceError> = pref.save(minutes.coerceAtLeast(0))
}

class GetBackupEnabledUseCase @Inject constructor(private val pref: BackupEnabledPreference) {
    operator fun invoke(): Flow<Result<Boolean, PreferenceError>> = pref.value
}

class SetBackupEnabledUseCase @Inject constructor(private val pref: BackupEnabledPreference) {
    suspend operator fun invoke(enabled: Boolean): Result<Boolean, PreferenceError> = pref.save(enabled)
}

class GetBackupOnlyOnWifiUseCase @Inject constructor(private val pref: BackupOnlyOnWifiPreference) {
    operator fun invoke(): Flow<Result<Boolean, PreferenceError>> = pref.value
}

class SetBackupOnlyOnWifiUseCase @Inject constructor(private val pref: BackupOnlyOnWifiPreference) {
    suspend operator fun invoke(enabled: Boolean): Result<Boolean, PreferenceError> = pref.save(enabled)
}

class GetBackupLocationUseCase @Inject constructor(private val pref: BackupLocationPreference) {
    operator fun invoke(): Flow<Result<String, PreferenceError>> = pref.value
}

class SetBackupLocationUseCase @Inject constructor(private val pref: BackupLocationPreference) {
    suspend operator fun invoke(location: String): Result<String, PreferenceError> = pref.save(location)
}

class GetSyncEnabledUseCase @Inject constructor(private val pref: SyncEnabledPreference) {
    operator fun invoke(): Flow<Result<Boolean, PreferenceError>> = pref.value
}

class SetSyncEnabledUseCase @Inject constructor(private val pref: SyncEnabledPreference) {
    suspend operator fun invoke(enabled: Boolean): Result<Boolean, PreferenceError> = pref.save(enabled)
}

/**
 * Aggregate of reminder/notification, security, backup and sync preference use cases.
 */
data class SystemPreferencesUseCases @Inject constructor(
    val getNotificationsEnabled: GetNotificationsEnabledUseCase,
    val setNotificationsEnabled: SetNotificationsEnabledUseCase,
    val getReminderLeadMinutes: GetReminderLeadMinutesUseCase,
    val setReminderLeadMinutes: SetReminderLeadMinutesUseCase,
    val getReminderSoundEnabled: GetReminderSoundEnabledUseCase,
    val setReminderSoundEnabled: SetReminderSoundEnabledUseCase,
    val getReminderVibrationEnabled: GetReminderVibrationEnabledUseCase,
    val setReminderVibrationEnabled: SetReminderVibrationEnabledUseCase,
    val getBiometricLockEnabled: GetBiometricLockEnabledUseCase,
    val setBiometricLockEnabled: SetBiometricLockEnabledUseCase,
    val getAutoLockMinutes: GetAutoLockMinutesUseCase,
    val setAutoLockMinutes: SetAutoLockMinutesUseCase,
    val getBackupEnabled: GetBackupEnabledUseCase,
    val setBackupEnabled: SetBackupEnabledUseCase,
    val getBackupOnlyOnWifi: GetBackupOnlyOnWifiUseCase,
    val setBackupOnlyOnWifi: SetBackupOnlyOnWifiUseCase,
    val getBackupLocation: GetBackupLocationUseCase,
    val setBackupLocation: SetBackupLocationUseCase,
    val getSyncEnabled: GetSyncEnabledUseCase,
    val setSyncEnabled: SetSyncEnabledUseCase
)