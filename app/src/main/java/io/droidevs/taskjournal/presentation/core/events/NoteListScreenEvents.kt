package io.droidevs.taskjournal.presentation.core.events

sealed interface NoteListScreenEvent {

    data class NavigateToNoteDetail(val noteId: Long) : NoteListScreenEvent

    data object NavigateToNoteCreate : NoteListScreenEvent

    data object NavigateToCategoryList : NoteListScreenEvent

    data object NavigateToTrash : NoteListScreenEvent

    data object NavigateToSettings : NoteListScreenEvent

    data object NoteTrashedSuccessfully : NoteListScreenEvent

    data object NoteTrashFailed : NoteListScreenEvent

    data object NotesTrashedSuccessfully : NoteListScreenEvent

    data object NoteActionFailed : NoteListScreenEvent
}