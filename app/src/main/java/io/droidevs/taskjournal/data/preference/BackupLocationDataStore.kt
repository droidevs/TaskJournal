package io.droidevs.taskjournal.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.droidevs.taskjournal.data.preference.delegate.identityPreferenceDelegate
import io.droidevs.taskjournal.domain.preference.BackupLocationPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupLocationDataStore @Inject constructor(
    dataStore: DataStore<Preferences>
) : BackupLocationPreference {

    private val delegate = identityPreferenceDelegate(
        dataStore = dataStore,
        key = PreferenceKeys.BACKUP_LOCATION,
        defaultValue = ""
    )

    override val value: Flow<Result<String, PreferenceError>> = delegate.flow

    override suspend fun save(value: String): Result<String, PreferenceError> = delegate.set(value)
}
