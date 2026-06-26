package io.droidevs.taskjournal.domain.usecase

import io.droidevs.taskjournal.domain.dispatchers.AppDispatchersProvider
import io.droidevs.taskjournal.domain.model.Comment
import io.droidevs.taskjournal.domain.repository.CommentRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCommentsForNoteUseCase @Inject constructor(
    private val repository: CommentRepository,
    private val dispatchers: AppDispatchersProvider
) {
    operator fun invoke(noteId: Long): Flow<Result<List<Comment>, DatabaseError>> =
        repository.getCommentsForNote(noteId).flowOn(dispatchers.io)
}

class InsertCommentUseCase @Inject constructor(
    private val repository: CommentRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(comment: Comment): Result<Long, DatabaseError> =
        withContext(dispatchers.io) { repository.insertComment(comment) }
}

class UpdateCommentUseCase @Inject constructor(
    private val repository: CommentRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(comment: Comment): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) { repository.updateComment(comment) }
}

class DeleteCommentUseCase @Inject constructor(
    private val repository: CommentRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(comment: Comment): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) { repository.deleteComment(comment) }

    suspend operator fun invoke(id: Long): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) { repository.deleteCommentById(id) }
}

data class CommentUseCases @Inject constructor(
    val getForNote: GetCommentsForNoteUseCase,
    val insert: InsertCommentUseCase,
    val update: UpdateCommentUseCase,
    val delete: DeleteCommentUseCase
)