package io.droidevs.taskjournal.domain.usecase.preference

import io.droidevs.taskjournal.domain.model.CalendarViewMode
import io.droidevs.taskjournal.domain.model.NoteListLayout
import io.droidevs.taskjournal.domain.model.StartScreen
import io.droidevs.taskjournal.domain.model.ThemeMode
import io.droidevs.taskjournal.domain.preference.CalendarViewModePreference
import io.droidevs.taskjournal.domain.preference.DynamicColorPreference
import io.droidevs.taskjournal.domain.preference.NoteListLayoutPreference
import io.droidevs.taskjournal.domain.preference.StartScreenPreference
import io.droidevs.taskjournal.domain.preference.ThemeModePreference
import io.droidevs.taskjournal.domain.preference.UseAmoledBlackPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemeModeUseCase @Inject constructor(private val pref: ThemeModePreference) {
    operator fun invoke(): Flow<Result<ThemeMode, PreferenceError>> = pref.value
}

class SetThemeModeUseCase @Inject constructor(private val pref: ThemeModePreference) {
    suspend operator fun invoke(mode: ThemeMode): Result<ThemeMode, PreferenceError> = pref.save(mode)
}

class GetDynamicColorUseCase @Inject constructor(private val pref: DynamicColorPreference) {
    operator fun invoke(): Flow<Result<Boolean, PreferenceError>> = pref.value
}

class SetDynamicColorUseCase @Inject constructor(private val pref: DynamicColorPreference) {
    suspend operator fun invoke(enabled: Boolean): Result<Boolean, PreferenceError> = pref.save(enabled)
}

class GetUseAmoledBlackUseCase @Inject constructor(private val pref: UseAmoledBlackPreference) {
    operator fun invoke(): Flow<Result<Boolean, PreferenceError>> = pref.value
}

class SetUseAmoledBlackUseCase @Inject constructor(private val pref: UseAmoledBlackPreference) {
    suspend operator fun invoke(enabled: Boolean): Result<Boolean, PreferenceError> = pref.save(enabled)
}

class GetStartScreenUseCase @Inject constructor(private val pref: StartScreenPreference) {
    operator fun invoke(): Flow<Result<StartScreen, PreferenceError>> = pref.value
}

class SetStartScreenUseCase @Inject constructor(private val pref: StartScreenPreference) {
    suspend operator fun invoke(screen: StartScreen): Result<StartScreen, PreferenceError> = pref.save(screen)
}

class GetNoteListLayoutUseCase @Inject constructor(private val pref: NoteListLayoutPreference) {
    operator fun invoke(): Flow<Result<NoteListLayout, PreferenceError>> = pref.value
}

class SetNoteListLayoutUseCase @Inject constructor(private val pref: NoteListLayoutPreference) {
    suspend operator fun invoke(layout: NoteListLayout): Result<NoteListLayout, PreferenceError> = pref.save(layout)
}

class GetCalendarViewModeUseCase @Inject constructor(private val pref: CalendarViewModePreference) {
    operator fun invoke(): Flow<Result<CalendarViewMode, PreferenceError>> = pref.value
}

class SetCalendarViewModeUseCase @Inject constructor(private val pref: CalendarViewModePreference) {
    suspend operator fun invoke(mode: CalendarViewMode): Result<CalendarViewMode, PreferenceError> = pref.save(mode)
}

/**
 * Aggregate of appearance-related preference use cases (theme, color, layout, start screen).
 */
data class AppearancePreferencesUseCases @Inject constructor(
    val getThemeMode: GetThemeModeUseCase,
    val setThemeMode: SetThemeModeUseCase,
    val getDynamicColor: GetDynamicColorUseCase,
    val setDynamicColor: SetDynamicColorUseCase,
    val getUseAmoledBlack: GetUseAmoledBlackUseCase,
    val setUseAmoledBlack: SetUseAmoledBlackUseCase,
    val getStartScreen: GetStartScreenUseCase,
    val setStartScreen: SetStartScreenUseCase,
    val getNoteListLayout: GetNoteListLayoutUseCase,
    val setNoteListLayout: SetNoteListLayoutUseCase,
    val getCalendarViewMode: GetCalendarViewModeUseCase,
    val setCalendarViewMode: SetCalendarViewModeUseCase
)