package io.droidevs.taskjournal.data.repository

import io.droidevs.taskjournal.data.local.dao.ChecklistItemDao
import io.droidevs.taskjournal.data.local.exceptions.asDatabaseResultFlowPreservingResult
import io.droidevs.taskjournal.data.local.exceptions.runCatchingDatabaseResult
import io.droidevs.taskjournal.data.mappers.toDomain
import io.droidevs.taskjournal.data.mappers.toEntity
import io.droidevs.taskjournal.domain.model.ChecklistItem
import io.droidevs.taskjournal.domain.repository.ChecklistItemRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChecklistItemRepositoryImpl @Inject constructor(
    private val dao: ChecklistItemDao
) : ChecklistItemRepository {
    override fun getChecklistItemsForNote(noteId: Long): Flow<Result<List<ChecklistItem>, DatabaseError>> {
        return dao.observeByNoteId(noteId)
            .map { items -> Result.Success(items.map { it.toDomain() }) }
            .asDatabaseResultFlowPreservingResult()
    }

    override suspend fun insertChecklistItem(item: ChecklistItem): Result<Long, DatabaseError> {
        return runCatchingDatabaseResult { dao.insert(item.toEntity()) }
    }

    override suspend fun updateChecklistItem(item: ChecklistItem): Result<Unit, DatabaseError> {
        return runCatchingDatabaseResult { dao.update(item.toEntity()) }
    }

    override suspend fun deleteChecklistItem(item: ChecklistItem): Result<Unit, DatabaseError> {
        return runCatchingDatabaseResult { dao.delete(item.toEntity()) }
    }

    override suspend fun deleteChecklistItemById(id: Long): Result<Unit, DatabaseError> {
        return runCatchingDatabaseResult { dao.deleteById(id) }
    }
}

