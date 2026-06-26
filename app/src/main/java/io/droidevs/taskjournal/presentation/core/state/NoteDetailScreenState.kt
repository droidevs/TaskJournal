package io.droidevs.taskjournal.presentation.core.state

import io.droidevs.taskjournal.domain.model.Attachment
import io.droidevs.taskjournal.domain.model.Category
import io.droidevs.taskjournal.domain.model.ChecklistItem
import io.droidevs.taskjournal.domain.model.Comment
import io.droidevs.taskjournal.domain.model.JournalMood
import io.droidevs.taskjournal.domain.model.Label
import io.droidevs.taskjournal.domain.model.NotePriority
import io.droidevs.taskjournal.domain.model.Reminder
import io.droidevs.taskjournal.domain.result.errors.DataError

data class NoteDetailScreenState(
    val noteId: Long? = null,
    val title: String = "",
    val content: String = "",
    val category: Category? = null,
    val labels: List<Label> = emptyList(),
    val priority: NotePriority = NotePriority.NONE,
    val mood: JournalMood? = null,
    val dueAt: Long? = null,
    val reminderAt: Long? = null,
    val isPinned: Boolean = false,
    val isCompleted: Boolean = false,
    val isArchived: Boolean = false,
    val isDeleted: Boolean = false,
    val attachments: List<Attachment> = emptyList(),
    val checklistItems: List<ChecklistItem> = emptyList(),
    val comments: List<Comment> = emptyList(),
    val reminders: List<Reminder> = emptyList(),
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isNewNote: Boolean = true,
    val isDirty: Boolean = false,
    val error: DataError? = null
)