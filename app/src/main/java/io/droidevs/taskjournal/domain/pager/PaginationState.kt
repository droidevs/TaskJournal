package io.droidevs.taskjournal.domain.pager

import io.droidevs.counterapp.domain.result.errors.DataError
import io.droidevs.taskjournal.domain.result.errors.DataError


sealed class PaginationState {
    object Refreshing : PaginationState()
    object Loading : PaginationState()
    object Idle : PaginationState()
    data class Error(val error: DataError) : PaginationState()
}