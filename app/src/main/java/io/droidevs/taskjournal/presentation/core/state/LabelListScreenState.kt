package io.droidevs.taskjournal.presentation.core.state

import io.droidevs.taskjournal.domain.model.Label
import io.droidevs.taskjournal.domain.result.errors.DataError

data class LabelListScreenState(
    val labels: List<Label> = emptyList(),
    val isLoading: Boolean = false,
    val error: DataError? = null,
    val page: Int = 1,
    val pageSize: Int = 20,
    val endReached: Boolean = false,
    val searchQuery: String = ""
)