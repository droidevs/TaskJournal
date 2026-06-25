package io.droidevs.taskjournal.domain.repository

import io.droidevs.taskjournal.domain.model.Label
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow

interface LabelRepository {
    fun getAllLabels(page: Int = 1, pageSize: Int = 20): Flow<Result<List<Label>, DatabaseError>>
    fun getLabelsByUsage(): Flow<Result<List<Label>, DatabaseError>>
    fun getLabelById(id: Long): Flow<Result<Label?, DatabaseError>>
    fun searchLabels(query: String, page: Int = 1, pageSize: Int = 20): Flow<Result<List<Label>, DatabaseError>>
    suspend fun insertLabel(label: Label): Result<Long, DatabaseError>
    suspend fun updateLabel(label: Label): Result<Unit, DatabaseError>
    suspend fun deleteLabel(label: Label): Result<Unit, DatabaseError>
    suspend fun setLabelsForNote(noteId: Long, labelIds: List<Long>): Result<Unit, DatabaseError>
}

