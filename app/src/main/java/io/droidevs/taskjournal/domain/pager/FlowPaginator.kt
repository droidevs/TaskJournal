package io.droidevs.counterapp.domain.pager

import io.droidevs.bmicalc.domain.pager.Paginator
import io.droidevs.counterapp.domain.result.Result
import io.droidevs.counterapp.domain.result.errors.DataError
import io.droidevs.counterapp.domain.result.onFailure
import io.droidevs.counterapp.domain.result.onSuccessSuspend
import io.droidevs.taskjournal.domain.pager.PaginationState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class FlowPaginator<Key, Item>(
    private val initialKey: Key,
    private  val onRequest: suspend (nextKey: Key) -> Result<List<Item>, DataError>,
    private val getNextKey: suspend (key: Key, items: List<Item>) -> Key
) : Paginator<Key, Item> {

    private var currentKey = initialKey
    private var isMakingRequest = false

    private val _paginatorFlow = MutableSharedFlow<List<Item>>(
        replay = 0,
        extraBufferCapacity = 64
    )
    val paginatorFlow: SharedFlow<List<Item>> = _paginatorFlow

    private val _state = MutableStateFlow<PaginationState>(PaginationState.Idle)
    val state: StateFlow<PaginationState> = _state

    override suspend fun loadNextPage() {
        if (isMakingRequest) return

        isMakingRequest = true
        _state.value = PaginationState.Loading
        onRequest(currentKey)
            .onSuccessSuspend {
                currentKey = getNextKey(currentKey, it)
                _paginatorFlow.emit(it)
            }.onFailure {
                _state.value = PaginationState.Error(it)
            }
        isMakingRequest = false
        _state.value = PaginationState.Idle
    }

    override suspend fun refresh(){
        if (isMakingRequest) return
        currentKey = initialKey
        _paginatorFlow.emit(emptyList())
        _state.value = PaginationState.Refreshing

        isMakingRequest = true

        loadNextPage()

        _state.value = PaginationState.Idle
        isMakingRequest = false
    }
}