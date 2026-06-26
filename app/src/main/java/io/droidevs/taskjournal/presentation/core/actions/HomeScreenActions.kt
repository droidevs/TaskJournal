package io.droidevs.taskjournal.presentation.core.actions

sealed interface HomeScreenAction {

    data object Refresh : HomeScreenAction

    data class OpenNote(val noteId: Long) : HomeScreenAction

    data object CreateNote : HomeScreenAction

    data object NavigateToNoteList : HomeScreenAction

    data object NavigateToCategories : HomeScreenAction

    data object NavigateToTrash : HomeScreenAction

    data object NavigateToSettings : HomeScreenAction

    data class TogglePin(val noteId: Long) : HomeScreenAction

    data class ToggleCompleted(val noteId: Long) : HomeScreenAction
}