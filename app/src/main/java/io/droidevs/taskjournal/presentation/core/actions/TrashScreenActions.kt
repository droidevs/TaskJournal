package io.droidevs.taskjournal.presentation.core.actions

sealed interface TrashScreenAction {

    data object LoadMoreNotes : TrashScreenAction

    data object RefreshTrash : TrashScreenAction

    data class RestoreNote(val noteId: Long) : TrashScreenAction

    data class PermanentlyDeleteNote(val noteId: Long) : TrashScreenAction

    data object EmptyTrash : TrashScreenAction

    data class OpenNote(val noteId: Long) : TrashScreenAction

    data object NavigateBack : TrashScreenAction
}