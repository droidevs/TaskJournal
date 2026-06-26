package io.droidevs.taskjournal.presentation.core.actions

sealed interface CategoryDetailScreenAction {

    data class NameChanged(val name: String) : CategoryDetailScreenAction

    data class ColorChanged(val colorHex: String) : CategoryDetailScreenAction

    data class IconChanged(val iconKey: String) : CategoryDetailScreenAction

    data object SaveCategory : CategoryDetailScreenAction

    data object DeleteCategory : CategoryDetailScreenAction

    data object NavigateBack : CategoryDetailScreenAction
}