package io.droidevs.taskjournal.presentation.core.actions

sealed interface LabelListScreenAction {

    data object LoadMoreLabels : LabelListScreenAction

    data object RefreshLabels : LabelListScreenAction

    data class Search(val query: String) : LabelListScreenAction

    data class CreateLabel(val name: String) : LabelListScreenAction

    data class RenameLabel(val labelId: Long, val newName: String) : LabelListScreenAction

    data class DeleteLabel(val labelId: Long) : LabelListScreenAction

    data object NavigateBack : LabelListScreenAction
}