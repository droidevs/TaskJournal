package io.droidevs.taskjournal.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.droidevs.taskjournal.domain.model.NoteOrder
import io.droidevs.taskjournal.domain.model.OrderType
import io.droidevs.taskjournal.domain.result.onFailureSuspend
import io.droidevs.taskjournal.domain.result.onSuccessSuspend
import io.droidevs.taskjournal.domain.usecase.NotesUseCases
import io.droidevs.taskjournal.presentation.core.actions.HomeScreenAction
import io.droidevs.taskjournal.presentation.core.events.HomeScreenEvent
import io.droidevs.taskjournal.presentation.core.state.HomeScreenState
import io.droidevs.taskjournal.presentation.models.mappers.toUi
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
class HomeViewModel @Inject constructor(
    private val noteUseCases: NotesUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()
        .onStart { loadDashboard() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeScreenState()
        )

    private val _events = MutableSharedFlow<HomeScreenEvent>()
    val events: SharedFlow<HomeScreenEvent> = _events.asSharedFlow()

    private fun loadDashboard() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            noteUseCases.getPinned(
                page = 1,
                pageSize = 10,
                order = NoteOrder.Date(OrderType.Descending)
            ).first()
                .onSuccessSuspend { pinned ->
                    _state.update { it.copy(pinnedNotes = pinned.map { note -> note.toUi() }) }
                }
                .onFailureSuspend { error -> _state.update { it.copy(error = error) } }

            noteUseCases.get(
                page = 1,
                pageSize = 10,
                order = NoteOrder.Date(OrderType.Descending)
            ).first()
                .onSuccessSuspend { recent ->
                    val dueSoon = recent.filter { it.dueAt != null && !it.isCompleted }
                    _state.update {
                        it.copy(
                            recentNotes = recent.map { note -> note.toUi() },
                            dueSoonNotes = dueSoon.map { note -> note.toUi() },
                            totalNotesCount = recent.size,
                            completedTodayCount = recent.count { note -> note.isCompleted },
                            isLoading = false
                        )
                    }
                }
                .onFailureSuspend { error -> _state.update { it.copy(error = error, isLoading = false) } }
        }
    }

    private fun togglePin(noteId: Long) {
        viewModelScope.launch {
            noteUseCases.getById(noteId).first()
                .onSuccessSuspend { note ->
                    if (note != null) {
                        noteUseCases.togglePin(note)
                            .onSuccessSuspend { loadDashboard() }
                            .onFailureSuspend { _events.emit(HomeScreenEvent.HomeActionFailed) }
                    }
                }
                .onFailureSuspend { _events.emit(HomeScreenEvent.HomeActionFailed) }
        }
    }

    private fun toggleCompleted(noteId: Long) {
        viewModelScope.launch {
            noteUseCases.getById(noteId).first()
                .onSuccessSuspend { note ->
                    if (note != null) {
                        noteUseCases.toggleCompleted(note)
                            .onSuccessSuspend { loadDashboard() }
                            .onFailureSuspend { _events.emit(HomeScreenEvent.HomeActionFailed) }
                    }
                }
                .onFailureSuspend { _events.emit(HomeScreenEvent.HomeActionFailed) }
        }
    }

    fun onAction(action: HomeScreenAction) {
        when (action) {
            HomeScreenAction.Refresh -> loadDashboard()
            is HomeScreenAction.OpenNote -> {
                viewModelScope.launch { _events.emit(HomeScreenEvent.NavigateToNoteDetail(action.noteId)) }
            }
            HomeScreenAction.CreateNote -> {
                viewModelScope.launch { _events.emit(HomeScreenEvent.NavigateToNoteCreate) }
            }
            HomeScreenAction.NavigateToNoteList -> {
                viewModelScope.launch { _events.emit(HomeScreenEvent.NavigateToNoteList) }
            }
            HomeScreenAction.NavigateToCategories -> {
                viewModelScope.launch { _events.emit(HomeScreenEvent.NavigateToCategoryList) }
            }
            HomeScreenAction.NavigateToTrash -> {
                viewModelScope.launch { _events.emit(HomeScreenEvent.NavigateToTrash) }
            }
            HomeScreenAction.NavigateToSettings -> {
                viewModelScope.launch { _events.emit(HomeScreenEvent.NavigateToSettings) }
            }
            is HomeScreenAction.TogglePin -> togglePin(action.noteId)
            is HomeScreenAction.ToggleCompleted -> toggleCompleted(action.noteId)
        }
    }
}