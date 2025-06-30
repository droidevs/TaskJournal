package io.droidevs.taskjournal.presentation.models.mappers

import io.droidevs.taskjournal.domain.model.Category
import io.droidevs.taskjournal.presentation.models.CategoryUi


fun Category.toUiModel(): CategoryUi{
    return CategoryUi(
        id = id,
        name = name,
        description = description,
        color = color,
        isSelected = false
    )
}