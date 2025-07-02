package io.droidevs.taskjournal.domain.preference

import kotlinx.coroutines.flow.Flow


interface MaxEndCountPreference {

    val maxEndCount: Flow<Int>

    suspend fun saveMaxEndCount(count: Int)

}