package io.droidevs.taskjournal.data.mappers

import io.droidevs.taskjournal.data.local.entity.ChecklistItemEntity
import io.droidevs.taskjournal.domain.model.ChecklistItem

fun ChecklistItemEntity.toDomain(): ChecklistItem = ChecklistItem(
    id = id,
    noteId = noteId,
    text = text,
    isChecked = isChecked,
    position = position,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun ChecklistItem.toEntity(): ChecklistItemEntity = ChecklistItemEntity(
    id = id,
    noteId = noteId,
    text = text,
    isChecked = isChecked,
    position = position,
    createdAt = createdAt,
    updatedAt = updatedAt
)

