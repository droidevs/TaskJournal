package io.droidevs.taskjournal.data.mappers

import io.droidevs.taskjournal.data.local.entity.CommentEntity
import io.droidevs.taskjournal.domain.model.Comment

fun CommentEntity.toDomain(): Comment = Comment(
    id = id,
    noteId = noteId,
    text = text,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun Comment.toEntity(): CommentEntity = CommentEntity(
    id = id,
    noteId = noteId,
    text = text,
    createdAt = createdAt,
    updatedAt = updatedAt
)

