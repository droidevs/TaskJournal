package io.droidevs.taskjournal.presentation.core.events

sealed interface CategoryDetailScreenEvent {

    data object NavigateBack : CategoryDetailScreenEvent

    data object CategorySavedSuccessfully : CategoryDetailScreenEvent

    data object CategorySaveFailed : CategoryDetailScreenEvent

    data object CategoryDeletedSuccessfully : CategoryDetailScreenEvent

    data object CategoryDeleteFailed : CategoryDetailScreenEvent

    data object ValidationNameRequired : CategoryDetailScreenEvent
}