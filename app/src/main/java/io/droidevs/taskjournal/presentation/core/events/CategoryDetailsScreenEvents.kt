package io.droidevs.taskjournal.presentation.core.events

sealed interface CategoryDetailsScreenEvent {

    object NavigateBack : CategoryDetailsScreenEvent

    data object DeletedSuccessfully : CategoryDetailsScreenEvent

    data object DeleteFailed : CategoryDetailsScreenEvent

    data object NavigateToEditScreen : CategoryDetailsScreenEvent

}