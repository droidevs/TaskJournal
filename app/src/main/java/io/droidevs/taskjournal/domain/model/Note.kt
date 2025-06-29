package io.droidevs.taskjournal.domain.model

import java.util.Date

data class Note(
    val id: Long = 0,
    val title: String,
    val  isMarkdown: Boolean,
    val isDeleted: Boolean,
    val content: String,
    val createdAt: Date,
    val updatedAt: Date,
    val isPinned: Boolean = false,
    val categoryId: Long? = null
) 