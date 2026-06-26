package io.droidevs.taskjournal.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.droidevs.taskjournal.data.preference.delegate.identityPreferenceDelegate
import io.droidevs.taskjournal.domain.preference.DynamicColorPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DynamicColorDataStore @Inject constructor(
    dataStore: DataStore<Preferences>
) : DynamicColorPreference {

    private val delegate = identityPreferenceDelegate(
        dataStore = dataStore,
        key = PreferenceKeys.DYNAMIC_COLOR,
        defaultValue = true
    )

    override val value: Flow<Result<Boolean, PreferenceError>> = delegate.flow

    override suspend fun save(value: Boolean): Result<Boolean, PreferenceError> = delegate.set(value)
}
