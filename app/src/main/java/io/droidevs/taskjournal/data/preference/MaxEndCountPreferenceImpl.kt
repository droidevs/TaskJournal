package io.droidevs.taskjournal.data.preference

import io.droidevs.taskjournal.domain.preference.MaxEndCountPreference
import kotlinx.coroutines.flow.Flow

class MaxEndCountPreferenceImpl : MaxEndCountPreference {

    override val maxEndCount: Flow<Int>
        get() = TODO("Not yet implemented")

    override suspend fun saveMaxEndCount(count: Int) {
        TODO("Not yet implemented")
    }
}