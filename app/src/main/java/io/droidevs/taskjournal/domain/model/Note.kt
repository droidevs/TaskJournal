package io.droidevs.taskjournal.domain.model

data class Note(
    val id: Long = 0,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long,
    val isDeleted: Boolean = false,
    val isPinned: Boolean = false,
    val isArchived: Boolean = false,
    val isCompleted: Boolean = false,
    val priority: NotePriority = NotePriority.NONE,
    val dueAt: Long? = null,
    val reminderAt: Long? = null,
    val completedAt: Long? = null,
    val archivedAt: Long? = null,
    val deletedAt: Long? = null,
    val recurrenceRule: String? = null,
    val mood: JournalMood? = null,
    val location: LocationSnapshot? = null,
    val category: Category? = null,
    val labels: List<Label> = emptyList(),
    val attachments: List<Attachment> = emptyList(),
    val checklistItems: List<ChecklistItem> = emptyList(),
    val comments: List<Comment> = emptyList(),
    val reminders: List<Reminder> = emptyList()
) 
