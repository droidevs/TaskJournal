package io.droidevs.taskjournal.data.mappers

import io.droidevs.taskjournal.data.local.entity.NoteEntity
import io.droidevs.taskjournal.data.local.entity.NoteWithCategory
import io.droidevs.taskjournal.domain.model.Note


fun NoteEntity.toDomain(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt,
        categoryId = category?.id
    )
}

fun NoteWithCategory.toDomain(): Note{
    return Note(
        id = note.id,
        title = note.title,
        content = note.content,
        createdAt = note.createdAt,
        updatedAt = note.updatedAt,
        isPinned = note.isPinned,
        category = category?.toDomainModel()
    )
}