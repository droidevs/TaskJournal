package io.droidevs.taskjournal.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.droidevs.taskjournal.domain.model.Label
import io.droidevs.taskjournal.domain.pager.DefaultPaginator
import io.droidevs.taskjournal.domain.result.onFailureSuspend
import io.droidevs.taskjournal.domain.result.onSuccessSuspend
import io.droidevs.taskjournal.domain.usecase.LabelUseCases
import io.droidevs.taskjournal.presentation.core.actions.LabelListScreenAction
import io.droidevs.taskjournal.presentation.core.events.LabelListScreenEvent
import io.droidevs.taskjournal.presentation.core.state.LabelListScreenState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LabelListViewModel @Inject constructor(
    private val labelUseCases: LabelUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(LabelListScreenState())
    val state: StateFlow<LabelListScreenState> = _state.asStateFlow()
        .onStart { loadMoreLabels() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = LabelListScreenState()
        )

    private val _events = MutableSharedFlow<LabelListScreenEvent>()
    val events: SharedFlow<LabelListScreenEvent> = _events.asSharedFlow()

    private val paginator = DefaultPaginator(
        initialKey = 1,
        onLoadUpdated = { isLoading -> _state.update { it.copy(isLoading = isLoading) } },
        getNextKey = { currentKey, _ -> currentKey + 1 },
        onRequest = { nextPage ->
            val query = state.value.searchQuery
            if (query.isBlank()) {
                labelUseCases.getAll(page = nextPage, pageSize = state.value.pageSize).first()
            } else {
                labelUseCases.search(query = query, page = nextPage, pageSize = state.value.pageSize).first()
            }
        },
        onError = { error -> _state.update { it.copy(error = error) } },
        onSuccess = { labels, newPage ->
            _state.update {
                it.copy(
                    labels = if (newPage == 2) labels else it.labels + labels,
                    page = newPage,
                    endReached = labels.isEmpty()
                )
            }
        }
    )

    private fun loadMoreLabels() {
        if (_state.value.isLoading || _state.value.endReached) return
        viewModelScope.launch { paginator.loadNextItems() }
    }

    private fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(labels = emptyList(), endReached = false, page = 1) }
            paginator.reset()
        }
    }

    private fun search(query: String) {
        _state.update { it.copy(searchQuery = query, labels = emptyList(), endReached = false, page = 1) }
        refresh()
    }

    private fun createLabel(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            labelUseCases.insert(Label(name = name.trim()))
                .onSuccessSuspend {
                    _events.emit(LabelListScreenEvent.LabelCreatedSuccessfully)
                    refresh()
                }
                .onFailureSuspend { _events.emit(LabelListScreenEvent.LabelCreateFailed) }
        }
    }

    private fun renameLabel(labelId: Long, newName: String) {
        if (newName.isBlank()) return
        val existing = _state.value.labels.find { it.id == labelId } ?: return
        viewModelScope.launch {
            labelUseCases.update(existing.copy(name = newName.trim()))
                .onSuccessSuspend {
                    _state.update { current ->
                        current.copy(labels = current.labels.map {
                            if (it.id == labelId) it.copy(name = newName.trim()) else it
                        })
                    }
                    _events.emit(LabelListScreenEvent.LabelRenamedSuccessfully)
                }
                .onFailureSuspend { _events.emit(LabelListScreenEvent.LabelRenameFailed) }
        }
    }

    private fun deleteLabel(labelId: Long) {
        val existing = _state.value.labels.find { it.id == labelId } ?: return
        viewModelScope.launch {
            labelUseCases.delete(existing)
                .onSuccessSuspend {
                    _state.update { current -> current.copy(labels = current.labels.filterNot { it.id == labelId }) }
                    _events.emit(LabelListScreenEvent.LabelDeletedSuccessfully)
                }
                .onFailureSuspend { _events.emit(LabelListScreenEvent.LabelDeleteFailed) }
        }
    }

    fun onAction(action: LabelListScreenAction) {
        when (action) {
            LabelListScreenAction.LoadMoreLabels -> loadMoreLabels()
            LabelListScreenAction.RefreshLabels -> refresh()
            is LabelListScreenAction.Search -> search(action.query)
            is LabelListScreenAction.CreateLabel -> createLabel(action.name)
            is LabelListScreenAction.RenameLabel -> renameLabel(action.labelId, action.newName)
            is LabelListScreenAction.DeleteLabel -> deleteLabel(action.labelId)
            LabelListScreenAction.NavigateBack -> {
                viewModelScope.launch { _events.emit(LabelListScreenEvent.NavigateBack) }
            }
        }
    }
}