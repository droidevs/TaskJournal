package io.droidevs.taskjournal.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.droidevs.taskjournal.data.preference.delegate.identityPreferenceDelegate
import io.droidevs.taskjournal.domain.preference.ShowPinnedFirstPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShowPinnedFirstDataStore @Inject constructor(
    dataStore: DataStore<Preferences>
) : ShowPinnedFirstPreference {

    private val delegate = identityPreferenceDelegate(
        dataStore = dataStore,
        key = PreferenceKeys.SHOW_PINNED_FIRST,
        defaultValue = true
    )

    override val value: Flow<Result<Boolean, PreferenceError>> = delegate.flow

    override suspend fun save(value: Boolean): Result<Boolean, PreferenceError> = delegate.set(value)
}
