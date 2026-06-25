package io.droidevs.taskjournal.presentation.models

import io.droidevs.taskjournal.presentation.utils.toDate
import java.time.LocalDateTime

data class NoteUi(
    val id : Long = -1,
    val title : String = "",
    val content: String = "",
    val date : String = System.currentTimeMillis().toDate(),
    val isSelected: Boolean = false,
    val isPinned: Boolean = false,
    val categoryName: String = "",
    val categoryId: Long = -1
)