package io.droidevs.taskjournal.presentation.core.actions


import io.droidevs.taskjournal.domain.model.JournalMood
import io.droidevs.taskjournal.domain.model.NotePriority

sealed interface NoteDetailScreenAction {

    data class TitleChanged(val title: String) : NoteDetailScreenAction

    data class ContentChanged(val content: String) : NoteDetailScreenAction

    data class CategoryChanged(val categoryId: Long?) : NoteDetailScreenAction

    data class PriorityChanged(val priority: NotePriority) : NoteDetailScreenAction

    data class MoodChanged(val mood: JournalMood?) : NoteDetailScreenAction

    data class DueDateChanged(val dueAt: Long?) : NoteDetailScreenAction

    data class ReminderChanged(val reminderAt: Long?) : NoteDetailScreenAction

    data class LabelsChanged(val labelIds: List<Long>) : NoteDetailScreenAction

    data object TogglePin : NoteDetailScreenAction

    data object ToggleCompleted : NoteDetailScreenAction

    data object SaveNote : NoteDetailScreenAction

    data object ArchiveNote : NoteDetailScreenAction

    data object DeleteNote : NoteDetailScreenAction

    data object RestoreNote : NoteDetailScreenAction

    data object PermanentlyDeleteNote : NoteDetailScreenAction

    data class AddComment(val text: String) : NoteDetailScreenAction

    data class DeleteComment(val commentId: Long) : NoteDetailScreenAction

    data class AddChecklistItem(val text: String) : NoteDetailScreenAction

    data class ToggleChecklistItem(val itemId: Long) : NoteDetailScreenAction

    data class DeleteChecklistItem(val itemId: Long) : NoteDetailScreenAction

    data object NavigateBack : NoteDetailScreenAction
}