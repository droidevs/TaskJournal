package io.droidevs.taskjournal.presentation.core.state

import io.droidevs.taskjournal.domain.result.errors.DataError

data class CategoryDetailScreenState(
    val categoryId: Long? = null,
    val name: String = "",
    val colorHex: String = "#6750A4",
    val iconKey: String = "folder",
    val isNewCategory: Boolean = true,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val error: DataError? = null
)