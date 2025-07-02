package io.droidevs.taskjournal.data.preference.delegate

import androidx.datastore.core.DataStore
import io.droidevs.taskjournal.data.preference.exceptions.flowCatchingPreference
import io.droidevs.taskjournal.data.preference.exceptions.runCatchingPreference
import io.droidevs.taskjournal.data.preference.exceptions.runCatchingPreferenceWithResult
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import io.droidevs.taskjournal.domain.result.Result
class ProtoDelegateEach<T, D>(
    private val dataStore: DataStore<D>,
    private val defaultValue: T?,
    private val getter: (D) -> T?,
    private val setter: (D, T?) -> D
) {

    val flow: Flow<Result<T?, PreferenceError>> by lazy {
        flowCatchingPreference {
            dataStore.data.map { getter(it) ?: defaultValue }
        }
    }

    suspend fun get(): Result<T?, PreferenceError> = runCatchingPreferenceWithResult {
        flow.first()
    }

    suspend fun set(value: T?): Result<D, PreferenceError> = runCatchingPreference {
        dataStore.updateData { prefs ->
            setter(prefs, value)
        }
    }
}
