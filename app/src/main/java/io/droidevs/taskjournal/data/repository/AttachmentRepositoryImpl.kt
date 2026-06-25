package io.droidevs.taskjournal.data.repository

import io.droidevs.taskjournal.data.local.dao.AttachmentDao
import io.droidevs.taskjournal.data.local.exceptions.asDatabaseResultFlowPreservingResult
import io.droidevs.taskjournal.data.local.exceptions.runCatchingDatabaseResult
import io.droidevs.taskjournal.data.mappers.toDomain
import io.droidevs.taskjournal.data.mappers.toEntity
import io.droidevs.taskjournal.domain.model.Attachment
import io.droidevs.taskjournal.domain.repository.AttachmentRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AttachmentRepositoryImpl @Inject constructor(
    private val dao: AttachmentDao
) : AttachmentRepository {
    override fun getAttachmentsForNote(noteId: Long): Flow<Result<List<Attachment>, DatabaseError>> {
        return dao.observeByNoteId(noteId)
            .map { attachments -> Result.Success(attachments.map { it.toDomain() }) }
            .asDatabaseResultFlowPreservingResult()
    }

    override suspend fun insertAttachment(attachment: Attachment): Result<Long, DatabaseError> {
        return runCatchingDatabaseResult { dao.insert(attachment.toEntity()) }
    }

    override suspend fun updateAttachment(attachment: Attachment): Result<Unit, DatabaseError> {
        return runCatchingDatabaseResult { dao.update(attachment.toEntity()) }
    }

    override suspend fun deleteAttachment(attachment: Attachment): Result<Unit, DatabaseError> {
        return runCatchingDatabaseResult { dao.delete(attachment.toEntity()) }
    }

    override suspend fun deleteAttachmentById(id: Long): Result<Unit, DatabaseError> {
        return runCatchingDatabaseResult { dao.deleteById(id) }
    }
}

