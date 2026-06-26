package io.droidevs.taskjournal.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.droidevs.taskjournal.data.preference.delegate.identityPreferenceDelegate
import io.droidevs.taskjournal.domain.preference.MaxFrequencyPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MaxFrequencyDataStore @Inject constructor(
    dataStore: DataStore<Preferences>
) : MaxFrequencyPreference {

    private val delegate = identityPreferenceDelegate(
        dataStore = dataStore,
        key = PreferenceKeys.MAX_FREQUENCY,
        defaultValue = 99
    )

    override val value: Flow<Result<Int, PreferenceError>> = delegate.flow

    override suspend fun save(value: Int): Result<Int, PreferenceError> = delegate.set(value)
}
