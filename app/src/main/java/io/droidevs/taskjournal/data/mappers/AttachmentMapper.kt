package io.droidevs.taskjournal.data.mappers

import io.droidevs.taskjournal.data.local.entity.AttachmentEntity
import io.droidevs.taskjournal.domain.model.Attachment

fun AttachmentEntity.toDomain(): Attachment = Attachment(
    id = id,
    noteId = noteId,
    type = type,
    path = path,
    fileName = fileName,
    description = description,
    mimeType = mimeType,
    sizeBytes = sizeBytes,
    createdAt = createdAt
)

fun Attachment.toEntity(): AttachmentEntity = AttachmentEntity(
    id = id,
    noteId = noteId,
    type = type,
    path = path,
    fileName = fileName,
    description = description,
    mimeType = mimeType,
    sizeBytes = sizeBytes,
    createdAt = createdAt
)

