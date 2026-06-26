package io.droidevs.taskjournal.domain.usecase.preference

import io.droidevs.taskjournal.domain.model.WeekStart
import io.droidevs.taskjournal.domain.preference.DateFormatPatternPreference
import io.droidevs.taskjournal.domain.preference.MaxEndCountPreference
import io.droidevs.taskjournal.domain.preference.MaxFrequencyPreference
import io.droidevs.taskjournal.domain.preference.Use24HourTimePreference
import io.droidevs.taskjournal.domain.preference.WeekStartPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeekStartUseCase @Inject constructor(private val pref: WeekStartPreference) {
    operator fun invoke(): Flow<Result<WeekStart, PreferenceError>> = pref.value
}

class SetWeekStartUseCase @Inject constructor(private val pref: WeekStartPreference) {
    suspend operator fun invoke(weekStart: WeekStart): Result<WeekStart, PreferenceError> = pref.save(weekStart)
}

class GetUse24HourTimeUseCase @Inject constructor(private val pref: Use24HourTimePreference) {
    operator fun invoke(): Flow<Result<Boolean, PreferenceError>> = pref.value
}

class SetUse24HourTimeUseCase @Inject constructor(private val pref: Use24HourTimePreference) {
    suspend operator fun invoke(use24Hour: Boolean): Result<Boolean, PreferenceError> = pref.save(use24Hour)
}

class GetDateFormatPatternUseCase @Inject constructor(private val pref: DateFormatPatternPreference) {
    operator fun invoke(): Flow<Result<String, PreferenceError>> = pref.value
}

class SetDateFormatPatternUseCase @Inject constructor(private val pref: DateFormatPatternPreference) {
    suspend operator fun invoke(pattern: String): Result<String, PreferenceError> = pref.save(pattern)
}

/**
 * Maximum allowed "every N <period>" frequency value in the recurrence picker.
 */
class GetMaxFrequencyUseCase @Inject constructor(private val pref: MaxFrequencyPreference) {
    operator fun invoke(): Flow<Result<Int, PreferenceError>> = pref.value
}

class SetMaxFrequencyUseCase @Inject constructor(private val pref: MaxFrequencyPreference) {
    suspend operator fun invoke(value: Int): Result<Int, PreferenceError> = pref.save(value.coerceAtLeast(1))
}

/**
 * Maximum allowed "ends after N events" count in the recurrence picker.
 */
class GetMaxEndCountUseCase @Inject constructor(private val pref: MaxEndCountPreference) {
    operator fun invoke(): Flow<Result<Int, PreferenceError>> = pref.value
}

class SetMaxEndCountUseCase @Inject constructor(private val pref: MaxEndCountPreference) {
    suspend operator fun invoke(value: Int): Result<Int, PreferenceError> = pref.save(value.coerceAtLeast(1))
}

/**
 * Aggregate of date/time formatting and recurrence-rule limit preference use cases.
 */
data class DateTimePreferencesUseCases @Inject constructor(
    val getWeekStart: GetWeekStartUseCase,
    val setWeekStart: SetWeekStartUseCase,
    val getUse24HourTime: GetUse24HourTimeUseCase,
    val setUse24HourTime: SetUse24HourTimeUseCase,
    val getDateFormatPattern: GetDateFormatPatternUseCase,
    val setDateFormatPattern: SetDateFormatPatternUseCase,
    val getMaxFrequency: GetMaxFrequencyUseCase,
    val setMaxFrequency: SetMaxFrequencyUseCase,
    val getMaxEndCount: GetMaxEndCountUseCase,
    val setMaxEndCount: SetMaxEndCountUseCase
)