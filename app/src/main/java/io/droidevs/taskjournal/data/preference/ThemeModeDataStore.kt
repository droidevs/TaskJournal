package io.droidevs.taskjournal.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.droidevs.taskjournal.data.preference.delegate.enumPreferenceDelegate
import io.droidevs.taskjournal.domain.model.ThemeMode
import io.droidevs.taskjournal.domain.preference.ThemeModePreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeModeDataStore @Inject constructor(
    dataStore: DataStore<Preferences>
) : ThemeModePreference {

    private val delegate = enumPreferenceDelegate(
        dataStore = dataStore,
        key = PreferenceKeys.THEME_MODE,
        defaultValue = ThemeMode.SYSTEM
    )

    override val value: Flow<Result<ThemeMode, PreferenceError>> = delegate.flow

    override suspend fun save(value: ThemeMode): Result<ThemeMode, PreferenceError> = delegate.set(value)
}