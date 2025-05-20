package io.droidevs.taskjournal.domain.model

data class Category(
    val id: Long = 0,
    val name: String,
    val description: String,
    val color: Int // Color resource ID
) 