package io.droidevs.taskjournal.domain.repository

import io.droidevs.taskjournal.domain.model.Attachment
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow

interface AttachmentRepository {
    fun getAttachmentsForNote(noteId: Long): Flow<Result<List<Attachment>, DatabaseError>>
    suspend fun insertAttachment(attachment: Attachment): Result<Long, DatabaseError>
    suspend fun updateAttachment(attachment: Attachment): Result<Unit, DatabaseError>
    suspend fun deleteAttachment(attachment: Attachment): Result<Unit, DatabaseError>
    suspend fun deleteAttachmentById(id: Long): Result<Unit, DatabaseError>
}

