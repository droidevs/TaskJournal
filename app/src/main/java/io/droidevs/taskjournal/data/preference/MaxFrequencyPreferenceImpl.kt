package io.droidevs.taskjournal.data.preference

import io.droidevs.taskjournal.domain.preference.AppPreferencesPreference
import io.droidevs.taskjournal.domain.preference.MaxFrequencyPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MaxFrequencyPreferenceImpl @Inject constructor(
    private val appPreferences: AppPreferencesPreference
) : MaxFrequencyPreference {
    override val frequency: Flow<Int> = appPreferences.getPreferences().map { result ->
        result.getOrNull()?.maxFrequency ?: 99
    }

    override suspend fun saveFrequency(frequency: Int) {
        appPreferences.updatePreferences { it.copy(maxFrequency = frequency.coerceAtLeast(1)) }
    }
}

