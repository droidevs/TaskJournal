package io.droidevs.taskjournal.domain.model

data class ChecklistItem(
    val id: Long = 0,
    val noteId: Long = 0,
    val text: String,
    val isChecked: Boolean = false,
    val position: Int = 0,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

