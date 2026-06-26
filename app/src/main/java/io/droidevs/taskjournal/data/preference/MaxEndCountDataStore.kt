package io.droidevs.taskjournal.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.droidevs.taskjournal.data.preference.delegate.identityPreferenceDelegate
import io.droidevs.taskjournal.domain.preference.MaxEndCountPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MaxEndCountDataStore @Inject constructor(
    dataStore: DataStore<Preferences>
) : MaxEndCountPreference {

    private val delegate = identityPreferenceDelegate(
        dataStore = dataStore,
        key = PreferenceKeys.MAX_END_COUNT,
        defaultValue = 999
    )

    override val value: Flow<Result<Int, PreferenceError>> = delegate.flow

    override suspend fun save(value: Int): Result<Int, PreferenceError> = delegate.set(value)
}
