package io.droidevs.taskjournal.domain.preference

import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow

interface ThemePreference {
    fun getTheme(): Flow<Result<Boolean, PreferenceError>>
    suspend fun setTheme(isDarkMode: Boolean): Result<Unit, PreferenceError>
} 
