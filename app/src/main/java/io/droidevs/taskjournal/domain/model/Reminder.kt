package io.droidevs.taskjournal.domain.model

data class Reminder(
    val id: Long = 0,
    val noteId: Long,
    val triggerAt: Long,
    val title: String? = null,
    val message: String? = null,
    val isEnabled: Boolean = true,
    val isDone: Boolean = false,
    val recurrenceRule: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)

