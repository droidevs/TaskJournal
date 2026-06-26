package io.droidevs.taskjournal.domain.usecase

import io.droidevs.taskjournal.domain.dispatchers.AppDispatchersProvider
import io.droidevs.taskjournal.domain.model.Attachment
import io.droidevs.taskjournal.domain.repository.AttachmentRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAttachmentsForNoteUseCase @Inject constructor(
    private val repository: AttachmentRepository,
    private val dispatchers: AppDispatchersProvider
) {
    operator fun invoke(noteId: Long): Flow<Result<List<Attachment>, DatabaseError>> =
        repository.getAttachmentsForNote(noteId).flowOn(dispatchers.io)
}

class InsertAttachmentUseCase @Inject constructor(
    private val repository: AttachmentRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(attachment: Attachment): Result<Long, DatabaseError> =
        withContext(dispatchers.io) { repository.insertAttachment(attachment) }
}

class UpdateAttachmentUseCase @Inject constructor(
    private val repository: AttachmentRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(attachment: Attachment): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) { repository.updateAttachment(attachment) }
}

class DeleteAttachmentUseCase @Inject constructor(
    private val repository: AttachmentRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(attachment: Attachment): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) { repository.deleteAttachment(attachment) }

    suspend operator fun invoke(id: Long): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) { repository.deleteAttachmentById(id) }
}

data class AttachmentUseCases @Inject constructor(
    val getForNote: GetAttachmentsForNoteUseCase,
    val insert: InsertAttachmentUseCase,
    val update: UpdateAttachmentUseCase,
    val delete: DeleteAttachmentUseCase
)