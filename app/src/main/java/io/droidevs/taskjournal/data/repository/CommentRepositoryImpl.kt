package io.droidevs.taskjournal.data.repository

import io.droidevs.taskjournal.data.local.dao.CommentDao
import io.droidevs.taskjournal.data.local.exceptions.asDatabaseResultFlowPreservingResult
import io.droidevs.taskjournal.data.local.exceptions.runCatchingDatabaseResult
import io.droidevs.taskjournal.data.mappers.toDomain
import io.droidevs.taskjournal.data.mappers.toEntity
import io.droidevs.taskjournal.domain.model.Comment
import io.droidevs.taskjournal.domain.repository.CommentRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val dao: CommentDao
) : CommentRepository {
    override fun getCommentsForNote(noteId: Long): Flow<Result<List<Comment>, DatabaseError>> {
        return dao.observeByNoteId(noteId)
            .map { comments -> Result.Success(comments.map { it.toDomain() }) }
            .asDatabaseResultFlowPreservingResult()
    }

    override suspend fun insertComment(comment: Comment): Result<Long, DatabaseError> {
        return runCatchingDatabaseResult { dao.insert(comment.toEntity()) }
    }

    override suspend fun updateComment(comment: Comment): Result<Unit, DatabaseError> {
        return runCatchingDatabaseResult { dao.update(comment.toEntity()) }
    }

    override suspend fun deleteComment(comment: Comment): Result<Unit, DatabaseError> {
        return runCatchingDatabaseResult { dao.delete(comment.toEntity()) }
    }

    override suspend fun deleteCommentById(id: Long): Result<Unit, DatabaseError> {
        return runCatchingDatabaseResult { dao.deleteById(id) }
    }
}

