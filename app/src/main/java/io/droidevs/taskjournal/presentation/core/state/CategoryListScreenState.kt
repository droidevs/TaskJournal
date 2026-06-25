package io.droidevs.taskjournal.presentation.core.state

import io.droidevs.taskjournal.domain.model.Category
import io.droidevs.taskjournal.domain.result.errors.DataError
import io.droidevs.taskjournal.presentation.models.CategoryUi

data class CategoryListScreenState(
    val categories: List<CategoryUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: DataError? = null,
    val page: Int = 0,
    val endReached: Boolean = false
)