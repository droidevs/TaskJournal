package io.droidevs.taskjournal.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.droidevs.taskjournal.data.preference.delegate.identityPreferenceDelegate
import io.droidevs.taskjournal.domain.preference.DateFormatPatternPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateFormatPatternDataStore @Inject constructor(
    dataStore: DataStore<Preferences>
) : DateFormatPatternPreference {

    private val delegate = identityPreferenceDelegate(
        dataStore = dataStore,
        key = PreferenceKeys.DATE_FORMAT_PATTERN,
        defaultValue = "system"
    )

    override val value: Flow<Result<String, PreferenceError>> = delegate.flow

    override suspend fun save(value: String): Result<String, PreferenceError> = delegate.set(value)
}
