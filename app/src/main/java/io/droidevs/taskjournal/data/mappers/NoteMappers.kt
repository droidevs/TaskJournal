package io.droidevs.taskjournal.data.mappers

import io.droidevs.taskjournal.data.local.entity.NoteEntity
import io.droidevs.taskjournal.data.local.entity.NoteWithDetails
import io.droidevs.taskjournal.domain.model.LocationSnapshot
import io.droidevs.taskjournal.domain.model.Note


fun NoteEntity.toDomain(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isDeleted = isDeleted,
        isPinned = isPinned,
        isArchived = isArchived,
        isCompleted = isCompleted,
        priority = priority,
        dueAt = dueAt,
        reminderAt = reminderAt,
        completedAt = completedAt,
        archivedAt = archivedAt,
        deletedAt = deletedAt,
        recurrenceRule = recurrenceRule,
        mood = mood,
        location = toLocationSnapshot()
    )
}

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        content = content,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt,
        isPinned = isPinned,
        categoryId = category?.id,
        isArchived = isArchived,
        isCompleted = isCompleted,
        priority = priority,
        dueAt = dueAt,
        reminderAt = reminderAt,
        completedAt = completedAt,
        archivedAt = archivedAt,
        deletedAt = deletedAt,
        recurrenceRule = recurrenceRule,
        mood = mood,
        locationName = location?.name,
        locationLatitude = location?.latitude,
        locationLongitude = location?.longitude,
        weatherSummary = location?.weatherSummary
    )
}

fun NoteWithDetails.toDomain(): Note{
    return Note(
        id = note.id,
        title = note.title,
        content = note.content,
        createdAt = note.createdAt,
        updatedAt = note.updatedAt,
        isDeleted = note.isDeleted,
        isPinned = note.isPinned,
        isArchived = note.isArchived,
        isCompleted = note.isCompleted,
        priority = note.priority,
        dueAt = note.dueAt,
        reminderAt = note.reminderAt,
        completedAt = note.completedAt,
        archivedAt = note.archivedAt,
        deletedAt = note.deletedAt,
        recurrenceRule = note.recurrenceRule,
        mood = note.mood,
        location = note.toLocationSnapshot(),
        category = category?.toDomainModel(),
        labels = labels.map { it.toDomain() },
        attachments = attachments.map { it.toDomain() },
        checklistItems = checklistItems.map { it.toDomain() },
        comments = comments.map { it.toDomain() },
        reminders = reminders.map { it.toDomain() }
    )
}

private fun NoteEntity.toLocationSnapshot(): LocationSnapshot? {
    if (locationName == null && locationLatitude == null && locationLongitude == null && weatherSummary == null) {
        return null
    }

    return LocationSnapshot(
        name = locationName,
        latitude = locationLatitude,
        longitude = locationLongitude,
        weatherSummary = weatherSummary
    )
}
