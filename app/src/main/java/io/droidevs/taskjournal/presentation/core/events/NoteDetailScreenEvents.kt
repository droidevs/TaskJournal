package io.droidevs.taskjournal.presentation.core.events

sealed interface NoteDetailScreenEvent {

    data object NavigateBack : NoteDetailScreenEvent

    data object NoteSavedSuccessfully : NoteDetailScreenEvent

    data object NoteSaveFailed : NoteDetailScreenEvent

    data object NoteDeletedSuccessfully : NoteDetailScreenEvent

    data object NoteDeleteFailed : NoteDetailScreenEvent

    data object NoteRestoredSuccessfully : NoteDetailScreenEvent

    data object NoteArchivedSuccessfully : NoteDetailScreenEvent

    data object NoteActionFailed : NoteDetailScreenEvent

    data object ValidationTitleRequired : NoteDetailScreenEvent
}