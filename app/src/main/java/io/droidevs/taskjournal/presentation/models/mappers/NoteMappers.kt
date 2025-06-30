package io.droidevs.taskjournal.presentation.models.mappers

import io.droidevs.taskjournal.domain.model.Note
import io.droidevs.taskjournal.presentation.models.NoteUi


fun Note.toUiModel(): NoteUi {
    return NoteUi(
        id = id,
        title = title,
        content = content,
        isPinned = isPinned,
        categoryId = category?.id?: -1,
        categoryName = category?.name?: "",
        timestamp = createdAt,
    )
}