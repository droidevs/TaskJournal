package io.droidevs.taskjournal.domain.preference

import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import io.droidevs.taskjournal.domain.model.SortOrder
import kotlinx.coroutines.flow.Flow

interface SortOrderPreference {
    fun getSortOrder(): Flow<Result<SortOrder, PreferenceError>>
    suspend fun setSortOrder(sortOrder: SortOrder): Result<Unit, PreferenceError>
} 
