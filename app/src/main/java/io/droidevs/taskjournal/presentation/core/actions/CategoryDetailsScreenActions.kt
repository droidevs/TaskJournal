package io.droidevs.taskjournal.presentation.core.actions

sealed interface CategoryDetailsScreenAction {

    data object DeleteCategory : CategoryDetailsScreenAction

    data object UpdateCategory : CategoryDetailsScreenAction

}