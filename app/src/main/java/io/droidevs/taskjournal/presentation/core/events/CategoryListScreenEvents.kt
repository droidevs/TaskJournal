package io.droidevs.taskjournal.presentation.core.events

sealed interface CategoryListScreenEvent {

    data class NavigateToCategoryDetail(val categoryId: Long) : CategoryListScreenEvent

    data object NavigateToCategoryCreate : CategoryListScreenEvent

    data class NavigateToCategoryEdit(val categoryId: Long) : CategoryListScreenEvent

    data object NavigateBack : CategoryListScreenEvent

    data object CategoryDeletedSuccessfully : CategoryListScreenEvent

    data object CategoryDeleteFailed : CategoryListScreenEvent
}