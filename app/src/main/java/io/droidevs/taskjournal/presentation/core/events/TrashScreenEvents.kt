package io.droidevs.taskjournal.presentation.core.events

sealed interface TrashScreenEvent {

    data class NavigateToNoteDetail(val noteId: Long) : TrashScreenEvent

    data object NavigateBack : TrashScreenEvent

    data object NoteRestoredSuccessfully : TrashScreenEvent

    data object NoteDeletedSuccessfully : TrashScreenEvent

    data object TrashEmptiedSuccessfully : TrashScreenEvent

    data object TrashActionFailed : TrashScreenEvent
}