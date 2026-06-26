package io.droidevs.taskjournal.presentation.core.actions

sealed interface CategoryListScreenAction {

    data object LoadMoreCategories : CategoryListScreenAction

    data object RefreshCategories : CategoryListScreenAction

    data class Search(val query: String) : CategoryListScreenAction

    data class OpenCategory(val categoryId: Long) : CategoryListScreenAction

    data object CreateCategory : CategoryListScreenAction

    data class EditCategory(val categoryId: Long) : CategoryListScreenAction

    data class DeleteCategory(val categoryId: Long) : CategoryListScreenAction

    data object NavigateBack : CategoryListScreenAction
}