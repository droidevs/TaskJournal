package io.droidevs.taskjournal.presentation.core.state

import io.droidevs.taskjournal.domain.model.NoteOrder
import io.droidevs.taskjournal.domain.model.OrderType
import io.droidevs.taskjournal.domain.result.errors.DataError
import io.droidevs.taskjournal.presentation.models.NoteUi

data class NoteListScreenState(
    val notes: List<NoteUi> = emptyList(),
    val selectedNoteIds: Set<Long> = emptySet(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: DataError? = null,
    val page: Int = 1,
    val pageSize: Int = 20,
    val endReached: Boolean = false,
    val showPinnedOnly: Boolean = false,
    val searchQuery: String = "",
    val order: NoteOrder = NoteOrder.Date(OrderType.Descending)
) {
    val hasSelection: Boolean get() = selectedNoteIds.isNotEmpty()
}