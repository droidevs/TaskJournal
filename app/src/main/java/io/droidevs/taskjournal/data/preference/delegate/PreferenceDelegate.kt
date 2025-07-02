package io.droidevs.taskjournal.data.preference.delegate

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import io.droidevs.taskjournal.data.preference.exceptions.flowCatchingPreference
import io.droidevs.taskjournal.data.preference.exceptions.runCatchingPreference
import io.droidevs.taskjournal.data.preference.exceptions.runCatchingPreferenceWithResult
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import io.droidevs.taskjournal.domain.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


class PreferenceDelegate<T>(
    private val dataStore: DataStore<Preferences>,
    private val key: Preferences.Key<T>,
    private val defaultValue: T,
) {
    val flow : Flow<Result<T, PreferenceError>> by lazy {
        flowCatchingPreference {
            dataStore.data
                .map { it[key] ?: defaultValue }
        }
    }

    suspend fun get(): Result<T, PreferenceError> = runCatchingPreferenceWithResult {
        flow.first()
    }

    suspend fun set(value: T): Result<T, PreferenceError> = runCatchingPreference {
        dataStore.edit { prefs ->
            prefs[key] = value
        }.get(key)?: defaultValue
    }
}