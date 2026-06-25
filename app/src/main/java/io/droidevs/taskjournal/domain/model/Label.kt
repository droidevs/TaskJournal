package io.droidevs.taskjournal.domain.model

data class Label(
    val id: Long = 0,
    val name: String,
    val color: Int? = null,
    val hidden: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

