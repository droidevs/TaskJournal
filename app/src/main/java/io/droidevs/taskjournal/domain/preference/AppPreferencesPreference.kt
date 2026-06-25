package io.droidevs.taskjournal.domain.preference

import io.droidevs.taskjournal.domain.model.AppPreferences
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow

interface AppPreferencesPreference {
    fun getPreferences(): Flow<Result<AppPreferences, PreferenceError>>
    suspend fun setPreferences(preferences: AppPreferences): Result<Unit, PreferenceError>
    suspend fun updatePreferences(transform: (AppPreferences) -> AppPreferences): Result<Unit, PreferenceError>
}

