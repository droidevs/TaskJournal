package io.droidevs.taskjournal.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.droidevs.taskjournal.data.preference.delegate.enumPreferenceDelegate
import io.droidevs.taskjournal.domain.model.WeekStart
import io.droidevs.taskjournal.domain.preference.WeekStartPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeekStartDataStore @Inject constructor(
    dataStore: DataStore<Preferences>
) : WeekStartPreference {

    private val delegate = enumPreferenceDelegate(
        dataStore = dataStore,
        key = PreferenceKeys.WEEK_STARTS_ON,
        defaultValue = WeekStart.SYSTEM
    )

    override val value: Flow<Result<WeekStart, PreferenceError>> = delegate.flow

    override suspend fun save(value: WeekStart): Result<WeekStart, PreferenceError> = delegate.set(value)
}
