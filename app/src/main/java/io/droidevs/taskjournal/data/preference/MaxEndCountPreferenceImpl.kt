package io.droidevs.taskjournal.data.preference

import io.droidevs.taskjournal.domain.preference.AppPreferencesPreference
import io.droidevs.taskjournal.domain.preference.MaxEndCountPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MaxEndCountPreferenceImpl @Inject constructor(
    private val appPreferences: AppPreferencesPreference
) : MaxEndCountPreference {
    override val maxEndCount: Flow<Int> = appPreferences.getPreferences().map { result ->
        result.getOrNull()?.maxEndCount ?: 999
    }

    override suspend fun saveMaxEndCount(count: Int) {
        appPreferences.updatePreferences { it.copy(maxEndCount = count.coerceAtLeast(1)) }
    }
}

