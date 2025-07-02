package io.droidevs.taskjournal.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import io.droidevs.taskjournal.domain.preference.MaxFrequencyPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MaxFrequencyPreferenceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : MaxFrequencyPreference {
    private object Key {
        val MAX_FREQUENCY = intPreferencesKey("max_frequency")
    }
    private companion object { const val DEFAULT_VALUE = 99 }

    override val frequency: Flow<Int> = dataStore.data.map { it[Key.MAX_FREQUENCY] ?: DEFAULT_VALUE }

    override suspend fun saveFrequency(frequency: Int) {
        dataStore.edit { it[Key.MAX_FREQUENCY] = frequency }
    }
}