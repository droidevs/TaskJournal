package io.droidevs.taskjournal.presentation.core.state

import io.droidevs.taskjournal.domain.result.errors.DataError
import io.droidevs.taskjournal.presentation.models.NoteUi

data class HomeScreenState(
    val pinnedNotes: List<NoteUi> = emptyList(),
    val recentNotes: List<NoteUi> = emptyList(),
    val dueSoonNotes: List<NoteUi> = emptyList(),
    val totalNotesCount: Int = 0,
    val completedTodayCount: Int = 0,
    val isLoading: Boolean = false,
    val error: DataError? = null
)