package io.droidevs.taskjournal.presentation.core.state

import io.droidevs.taskjournal.domain.model.Category
import io.droidevs.taskjournal.domain.result.errors.DataError

data class CategoryDetailsScreenState(
    val category: Category? = null,
    val isLoading: Boolean = false,
    val error: DataError? = null
)