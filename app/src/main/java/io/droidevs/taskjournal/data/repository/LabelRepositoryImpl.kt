package io.droidevs.taskjournal.data.repository

import io.droidevs.taskjournal.data.local.dao.LabelDao
import io.droidevs.taskjournal.data.local.entity.LabelRef
import io.droidevs.taskjournal.data.local.exceptions.asDatabaseResultFlowPreservingResult
import io.droidevs.taskjournal.data.local.exceptions.runCatchingDatabaseResult
import io.droidevs.taskjournal.data.mappers.toDomain
import io.droidevs.taskjournal.data.mappers.toEntity
import io.droidevs.taskjournal.domain.model.Label
import io.droidevs.taskjournal.domain.repository.LabelRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LabelRepositoryImpl @Inject constructor(
    private val dao: LabelDao
) : LabelRepository {
    override fun getAllLabels(page: Int, pageSize: Int): Flow<Result<List<Label>, DatabaseError>> {
        return dao.getPaged(offset = safeOffset(page, pageSize), limit = safePageSize(pageSize))
            .map { labels -> Result.Success(labels.map { it.toDomain() }) }
            .asDatabaseResultFlowPreservingResult()
    }

    override fun getLabelsByUsage(): Flow<Result<List<Label>, DatabaseError>> {
        return dao.getAllByUsage()
            .map { labels -> Result.Success(labels.map { it.toDomain() }) }
            .asDatabaseResultFlowPreservingResult()
    }

    override fun getLabelById(id: Long): Flow<Result<Label?, DatabaseError>> {
        return dao.observeById(id)
            .map { label -> Result.Success(label?.toDomain()) }
            .asDatabaseResultFlowPreservingResult()
    }

    override fun searchLabels(
        query: String,
        page: Int,
        pageSize: Int
    ): Flow<Result<List<Label>, DatabaseError>> {
        return dao.search(query = query, offset = safeOffset(page, pageSize), limit = safePageSize(pageSize))
            .map { labels -> Result.Success(labels.map { it.toDomain() }) }
            .asDatabaseResultFlowPreservingResult()
    }

    override suspend fun insertLabel(label: Label): Result<Long, DatabaseError> {
        return runCatchingDatabaseResult { dao.insert(label.toEntity()) }
    }

    override suspend fun updateLabel(label: Label): Result<Unit, DatabaseError> {
        return runCatchingDatabaseResult { dao.update(label.toEntity()) }
    }

    override suspend fun deleteLabel(label: Label): Result<Unit, DatabaseError> {
        return runCatchingDatabaseResult { dao.delete(label.toEntity()) }
    }

    override suspend fun setLabelsForNote(noteId: Long, labelIds: List<Long>): Result<Unit, DatabaseError> {
        return runCatchingDatabaseResult {
            dao.clearRefsForNote(noteId)
            dao.insertRefs(labelIds.distinct().map { labelId -> LabelRef(noteId = noteId, labelId = labelId) })
        }
    }

    private fun safePageSize(pageSize: Int): Int = pageSize.coerceAtLeast(1)
    private fun safeOffset(page: Int, pageSize: Int): Int = (page.coerceAtLeast(1) - 1) * safePageSize(pageSize)
}

