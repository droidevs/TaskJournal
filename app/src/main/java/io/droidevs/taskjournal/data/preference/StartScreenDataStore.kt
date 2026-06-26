package io.droidevs.taskjournal.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.droidevs.taskjournal.data.preference.delegate.enumPreferenceDelegate
import io.droidevs.taskjournal.domain.model.StartScreen
import io.droidevs.taskjournal.domain.preference.StartScreenPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StartScreenDataStore @Inject constructor(
    dataStore: DataStore<Preferences>
) : StartScreenPreference {

    private val delegate = enumPreferenceDelegate(
        dataStore = dataStore,
        key = PreferenceKeys.START_SCREEN,
        defaultValue = StartScreen.HOME
    )

    override val value: Flow<Result<StartScreen, PreferenceError>> = delegate.flow

    override suspend fun save(value: StartScreen): Result<StartScreen, PreferenceError> = delegate.set(value)
}
