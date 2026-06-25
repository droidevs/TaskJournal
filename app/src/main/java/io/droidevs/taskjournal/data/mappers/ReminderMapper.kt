package io.droidevs.taskjournal.data.mappers

import io.droidevs.taskjournal.data.local.entity.ReminderEntity
import io.droidevs.taskjournal.domain.model.Reminder

fun ReminderEntity.toDomain(): Reminder = Reminder(
    id = id,
    noteId = noteId,
    triggerAt = triggerAt,
    title = title,
    message = message,
    isEnabled = isEnabled,
    isDone = isDone,
    recurrenceRule = recurrenceRule,
    createdAt = createdAt
)

fun Reminder.toEntity(): ReminderEntity = ReminderEntity(
    id = id,
    noteId = noteId,
    triggerAt = triggerAt,
    title = title,
    message = message,
    isEnabled = isEnabled,
    isDone = isDone,
    recurrenceRule = recurrenceRule,
    createdAt = createdAt
)

