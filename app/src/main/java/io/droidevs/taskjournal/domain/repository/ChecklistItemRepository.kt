package io.droidevs.taskjournal.domain.repository

import io.droidevs.taskjournal.domain.model.ChecklistItem
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow

interface ChecklistItemRepository {
    fun getChecklistItemsForNote(noteId: Long): Flow<Result<List<ChecklistItem>, DatabaseError>>
    suspend fun insertChecklistItem(item: ChecklistItem): Result<Long, DatabaseError>
    suspend fun updateChecklistItem(item: ChecklistItem): Result<Unit, DatabaseError>
    suspend fun deleteChecklistItem(item: ChecklistItem): Result<Unit, DatabaseError>
    suspend fun deleteChecklistItemById(id: Long): Result<Unit, DatabaseError>
}

