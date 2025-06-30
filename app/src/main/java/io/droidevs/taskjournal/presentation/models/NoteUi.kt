package io.droidevs.taskjournal.presentation.models

import java.time.LocalDateTime

data class NoteUi(
    val id : Long = -1,
    val title : String = "",
    val content: String = "",
    val timestamp : LocalDateTime = LocalDateTime.now(),
    val isSelected: Boolean = false,
    val isPinned: Boolean = false,
    val categoryName: String = "",
    val categoryId: Long = -1
)