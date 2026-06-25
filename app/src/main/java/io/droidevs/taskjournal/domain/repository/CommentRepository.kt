package io.droidevs.taskjournal.domain.repository

import io.droidevs.taskjournal.domain.model.Comment
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow

interface CommentRepository {
    fun getCommentsForNote(noteId: Long): Flow<Result<List<Comment>, DatabaseError>>
    suspend fun insertComment(comment: Comment): Result<Long, DatabaseError>
    suspend fun updateComment(comment: Comment): Result<Unit, DatabaseError>
    suspend fun deleteComment(comment: Comment): Result<Unit, DatabaseError>
    suspend fun deleteCommentById(id: Long): Result<Unit, DatabaseError>
}

