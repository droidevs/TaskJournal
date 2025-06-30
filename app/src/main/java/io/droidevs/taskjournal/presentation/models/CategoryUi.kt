package io.droidevs.taskjournal.presentation.models

import java.time.LocalDateTime

data class CategoryUi(
    val id: Long,
    val name: String,
    val color: Int,
    val description: String,
    val isSelected: Boolean
)