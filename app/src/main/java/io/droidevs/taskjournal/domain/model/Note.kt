package io.droidevs.taskjournal.domain.model

import java.time.LocalDateTime
import java.util.Date

data class Note(
    val id: Long = 0,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long,
    val isPinned: Boolean = false,
    val category : Category? = null
) 