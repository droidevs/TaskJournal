package io.droidevs.taskjournal.presentation.core.events

sealed interface HomeScreenEvent {

    data class NavigateToNoteDetail(val noteId: Long) : HomeScreenEvent

    data object NavigateToNoteCreate : HomeScreenEvent

    data object NavigateToNoteList : HomeScreenEvent

    data object NavigateToCategoryList : HomeScreenEvent

    data object NavigateToTrash : HomeScreenEvent

    data object NavigateToSettings : HomeScreenEvent

    data object HomeActionFailed : HomeScreenEvent
}