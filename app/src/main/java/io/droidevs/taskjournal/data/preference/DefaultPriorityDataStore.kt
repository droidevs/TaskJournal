package io.droidevs.taskjournal.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.droidevs.taskjournal.data.preference.delegate.enumPreferenceDelegate
import io.droidevs.taskjournal.domain.model.NotePriority
import io.droidevs.taskjournal.domain.preference.DefaultPriorityPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPriorityDataStore @Inject constructor(
    dataStore: DataStore<Preferences>
) : DefaultPriorityPreference {

    private val delegate = enumPreferenceDelegate(
        dataStore = dataStore,
        key = PreferenceKeys.DEFAULT_PRIORITY,
        defaultValue = NotePriority.NONE
    )

    override val value: Flow<Result<NotePriority, PreferenceError>> = delegate.flow

    override suspend fun save(value: NotePriority): Result<NotePriority, PreferenceError> = delegate.set(value)
}
