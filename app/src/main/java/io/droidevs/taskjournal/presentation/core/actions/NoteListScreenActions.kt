package io.droidevs.taskjournal.presentation.core.actions

import io.droidevs.taskjournal.domain.model.NoteOrder

sealed interface NoteListScreenAction {

    data object LoadMoreNotes : NoteListScreenAction

    data object RefreshNotes : NoteListScreenAction

    data class Search(val query: String) : NoteListScreenAction

    data class ChangeOrder(val order: NoteOrder) : NoteListScreenAction

    data object ToggleShowPinnedOnly : NoteListScreenAction

    data class SelectNote(val noteId: Long) : NoteListScreenAction

    data class DeselectNote(val noteId: Long) : NoteListScreenAction

    data object ClearSelection : NoteListScreenAction

    data class OpenNote(val noteId: Long) : NoteListScreenAction

    data object CreateNote : NoteListScreenAction

    data class TogglePin(val noteId: Long) : NoteListScreenAction

    data class ToggleCompleted(val noteId: Long) : NoteListScreenAction

    data class ArchiveNote(val noteId: Long) : NoteListScreenAction

    data class TrashNote(val noteId: Long) : NoteListScreenAction

    data object TrashSelectedNotes : NoteListScreenAction

    data object NavigateToCategories : NoteListScreenAction

    data object NavigateToTrash : NoteListScreenAction

    data object NavigateToSettings : NoteListScreenAction
}