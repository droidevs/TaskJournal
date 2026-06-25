package io.droidevs.taskjournal.domain.model

data class Comment(
    val id: Long = 0,
    val noteId: Long,
    val text: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

