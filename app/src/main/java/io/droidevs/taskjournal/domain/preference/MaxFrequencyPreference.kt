package io.droidevs.taskjournal.domain.preference

import kotlinx.coroutines.flow.Flow

interface MaxFrequencyPreference {

    val frequency: Flow<Int>

    suspend fun saveFrequency(frequency: Int)
}