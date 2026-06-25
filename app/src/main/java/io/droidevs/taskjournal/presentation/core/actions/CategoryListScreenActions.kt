package io.droidevs.taskjournal.presentation.core.actions

sealed interface CategoryListScreenAction {

    data object LoadMoreCategories : CategoryListScreenAction

    data object RefreshCategories : CategoryListScreenAction

    data class SelectCategory(val categoryId: Long) : CategoryListScreenAction

    data class DeselectCategory(val categoryId: Long) : CategoryListScreenAction

    data object SelectAllCategories : CategoryListScreenAction

    data object DeselectAllCategories : CategoryListScreenAction
}