package io.droidevs.taskjournal.presentation.core.events

sealed interface CategoryListScreenEvent {

    data object CategoryDeletedSuccessfully : CategoryListScreenEvent

    data object CategoryDeleteFailed : CategoryListScreenEvent

    data object NavigateToAddCategoryScreen : CategoryListScreenEvent

    data class NavigateToCategoryDetailScreen(val categoryId: Long) : CategoryListScreenEvent

    data object NavigateBack : CategoryListScreenEvent
}