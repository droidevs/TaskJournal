package io.droidevs.taskjournal.data.mappers

import io.droidevs.taskjournal.data.local.entity.NoteEntity
import io.droidevs.taskjournal.domain.model.Note


fun NoteEntity.toDomain(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isMarkdown = isMarkdown,
        isDeleted = isDeleted
    )
}

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        content = content,
        isMarkdown = isMarkdown,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}