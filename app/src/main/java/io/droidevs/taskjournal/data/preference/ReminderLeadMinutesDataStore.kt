package io.droidevs.taskjournal.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.droidevs.taskjournal.data.preference.delegate.identityPreferenceDelegate
import io.droidevs.taskjournal.domain.preference.ReminderLeadMinutesPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderLeadMinutesDataStore @Inject constructor(
    dataStore: DataStore<Preferences>
) : ReminderLeadMinutesPreference {

    private val delegate = identityPreferenceDelegate(
        dataStore = dataStore,
        key = PreferenceKeys.REMINDER_LEAD_MINUTES,
        defaultValue = 10
    )

    override val value: Flow<Result<Int, PreferenceError>> = delegate.flow

    override suspend fun save(value: Int): Result<Int, PreferenceError> = delegate.set(value)
}
