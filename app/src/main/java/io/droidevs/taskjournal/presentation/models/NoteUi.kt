package io.droidevs.taskjournal.presentation.models

import io.droidevs.taskjournal.domain.model.NotePriority

/**
 * Display-ready projection of [io.droidevs.taskjournal.domain.model.Note] for list/grid screens.
 * Keeps Compose list items decoupled from the full domain model (e.g. avoids passing around
 * checklist items or comments that the list row never renders).
 */
data class NoteUi(
    val id: Long,
    val title: String,
    val preview: String,
    val categoryName: String?,
    val categoryColorHex: String?,
    val priority: NotePriority,
    val isPinned: Boolean,
    val isCompleted: Boolean,
    val isArchived: Boolean,
    val dueAtLabel: String?,
    val updatedAtLabel: String,
    val wordCount: Int
)