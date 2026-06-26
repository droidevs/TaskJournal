package io.droidevs.taskjournal.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.droidevs.taskjournal.domain.model.NoteOrder
import io.droidevs.taskjournal.domain.pager.DefaultPaginator
import io.droidevs.taskjournal.domain.result.onFailure
import io.droidevs.taskjournal.domain.result.onFailureSuspend
import io.droidevs.taskjournal.domain.result.onSuccessSuspend
import io.droidevs.taskjournal.domain.usecase.CategoryUseCases
import io.droidevs.taskjournal.domain.usecase.NotesUseCases
import io.droidevs.taskjournal.presentation.core.actions.NoteListScreenAction
import io.droidevs.taskjournal.presentation.core.events.NoteListScreenEvent
import io.droidevs.taskjournal.presentation.core.state.NoteListScreenState
import io.droidevs.taskjournal.presentation.models.mappers.toUi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val noteUseCases: NotesUseCases
) : ViewModel() {

    private var isRefreshing = false

    private val _state = MutableStateFlow(NoteListScreenState())
    val state: StateFlow<NoteListScreenState> = _state.asStateFlow()
        .onStart { onStartLoading() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = NoteListScreenState()
        )

    private val _events = MutableSharedFlow<NoteListScreenEvent>()
    val events: SharedFlow<NoteListScreenEvent> = _events.asSharedFlow()

    @OptIn(ExperimentalStdlibApi::class)
    private val paginator = DefaultPaginator(
        initialKey = 1,
        onLoadUpdated = { isLoading ->
            _state.update { it.copy(isLoading = isLoading) }
        },
        getNextKey = { currentKey, items ->
            currentKey + 1
        },
        onRequest = { nextPage ->
            noteUseCases.get(
                page = nextPage,
                pageSize = state.value.pageSize,
                order = state.value.order
            ).first()
        },
        onError = { error ->
            _state.update { it.copy(error = error) }
        },
        onSuccess = { notes, newPage ->
            val newNotes = notes.map { it.toUi(
                categoryName = it.category?.name,
                categoryColorHex = it.category?.color?.toHexString(HexFormat.Default)
            ) }
            _state.update {
                it.copy(
                    notes = if (newPage == 1) newNotes else it.notes + newNotes,
                    page = newPage,
                    endReached = notes.isEmpty()
                )
            }
        }
    )

    private fun onStartLoading() {
        viewModelScope.launch {
            loadMoreNotes()
        }
    }

    private fun loadMoreNotes() {
        if (_state.value.isLoading) return
        if (_state.value.endReached) return
        viewModelScope.launch { paginator.loadNextItems() }
    }

    private fun refreshNotes() {
        viewModelScope.launch {
            isRefreshing = true
            _state.update { it.copy(endReached = false) }
            paginator.reset()
            paginator.loadNextItems()
        }
    }

    private fun search(query: String) {
        _state.update { it.copy(searchQuery = query, notes = emptyList(), endReached = false) }
        refreshNotes()
    }

    private fun changeOrder(order: NoteOrder) {
        _state.update { it.copy(order = order, notes = emptyList(), endReached = false) }
        refreshNotes()
    }

    private fun togglePin(noteId: Long) {
        val note = _state.value.notes.find { it.id == noteId } ?: return
        viewModelScope.launch {
            noteUseCases.getById(noteId).first()
                .onSuccessSuspend { domainNote ->
                    if (domainNote == null) return@onSuccessSuspend
                    noteUseCases.togglePin(domainNote)
                        .onFailureSuspend { _events.emit(NoteListScreenEvent.NoteActionFailed) }
                }
                .onFailureSuspend { _events.emit(NoteListScreenEvent.NoteActionFailed) }
        }
        // Optimistic UI flip while the write completes.
        _state.update { current ->
            current.copy(notes = current.notes.map { if (it.id == noteId) it.copy(isPinned = !it.isPinned) else it })
        }
    }

    private fun toggleCompleted(noteId: Long) {
        viewModelScope.launch {
            noteUseCases.getById(noteId).first()
                .onSuccessSuspend { domainNote ->
                    if (domainNote == null) return@onSuccessSuspend
                    noteUseCases.toggleCompleted(domainNote)
                        .onFailureSuspend { _events.emit(NoteListScreenEvent.NoteActionFailed) }
                }
                .onFailureSuspend { _events.emit(NoteListScreenEvent.NoteActionFailed) }
        }
        _state.update { current ->
            current.copy(notes = current.notes.map { if (it.id == noteId) it.copy(isCompleted = !it.isCompleted) else it })
        }
    }

    private fun archiveNote(noteId: Long) {
        viewModelScope.launch {
            noteUseCases.getById(noteId).first()
                .onSuccessSuspend { domainNote ->
                    if (domainNote == null) return@onSuccessSuspend
                    noteUseCases.archive(domainNote)
                        .onSuccessSuspend {
                            _state.update { current -> current.copy(notes = current.notes.filterNot { it.id == noteId }) }
                        }
                        .onFailureSuspend { _events.emit(NoteListScreenEvent.NoteActionFailed) }
                }
                .onFailureSuspend { _events.emit(NoteListScreenEvent.NoteActionFailed) }
        }
    }

    private fun trashNote(noteId: Long) {
        viewModelScope.launch {
            noteUseCases.getById(noteId).first()
                .onSuccessSuspend { domainNote ->
                    if (domainNote == null) return@onSuccessSuspend
                    noteUseCases.trash(domainNote)
                        .onSuccessSuspend {
                            _state.update { current -> current.copy(notes = current.notes.filterNot { it.id == noteId }) }
                            _events.emit(NoteListScreenEvent.NoteTrashedSuccessfully)
                        }
                        .onFailureSuspend { _events.emit(NoteListScreenEvent.NoteTrashFailed) }
                }
                .onFailureSuspend { _events.emit(NoteListScreenEvent.NoteTrashFailed) }
        }
    }

    private fun trashSelectedNotes() {
        val ids = _state.value.selectedNoteIds
        if (ids.isEmpty()) return
        viewModelScope.launch {
            var anyFailed = false
            ids.forEach { id ->
                noteUseCases.getById(id).first()
                    .onSuccessSuspend { domainNote ->
                        if (domainNote != null) {
                            noteUseCases.trash(domainNote).onFailure { anyFailed = true }
                        }
                    }
                    .onFailure { anyFailed = true }
            }
            _state.update { current ->
                current.copy(
                    notes = current.notes.filterNot { it.id in ids },
                    selectedNoteIds = emptySet()
                )
            }
            _events.emit(
                if (anyFailed) NoteListScreenEvent.NoteActionFailed
                else NoteListScreenEvent.NotesTrashedSuccessfully
            )
        }
    }

    fun onAction(action: NoteListScreenAction) {
        when (action) {
            NoteListScreenAction.LoadMoreNotes -> loadMoreNotes()
            NoteListScreenAction.RefreshNotes -> refreshNotes()
            is NoteListScreenAction.Search -> search(action.query)
            is NoteListScreenAction.ChangeOrder -> changeOrder(action.order)
            NoteListScreenAction.ToggleShowPinnedOnly -> {
                _state.update { it.copy(showPinnedOnly = !it.showPinnedOnly) }
            }
            is NoteListScreenAction.SelectNote -> {
                _state.update { it.copy(selectedNoteIds = it.selectedNoteIds + action.noteId) }
            }
            is NoteListScreenAction.DeselectNote -> {
                _state.update { it.copy(selectedNoteIds = it.selectedNoteIds - action.noteId) }
            }
            NoteListScreenAction.ClearSelection -> {
                _state.update { it.copy(selectedNoteIds = emptySet()) }
            }
            is NoteListScreenAction.OpenNote -> {
                viewModelScope.launch { _events.emit(NoteListScreenEvent.NavigateToNoteDetail(action.noteId)) }
            }
            NoteListScreenAction.CreateNote -> {
                viewModelScope.launch { _events.emit(NoteListScreenEvent.NavigateToNoteCreate) }
            }
            is NoteListScreenAction.TogglePin -> togglePin(action.noteId)
            is NoteListScreenAction.ToggleCompleted -> toggleCompleted(action.noteId)
            is NoteListScreenAction.ArchiveNote -> archiveNote(action.noteId)
            is NoteListScreenAction.TrashNote -> trashNote(action.noteId)
            NoteListScreenAction.TrashSelectedNotes -> trashSelectedNotes()
            NoteListScreenAction.NavigateToCategories -> {
                viewModelScope.launch { _events.emit(NoteListScreenEvent.NavigateToCategoryList) }
            }
            NoteListScreenAction.NavigateToTrash -> {
                viewModelScope.launch { _events.emit(NoteListScreenEvent.NavigateToTrash) }
            }
            NoteListScreenAction.NavigateToSettings -> {
                viewModelScope.launch { _events.emit(NoteListScreenEvent.NavigateToSettings) }
            }
        }
    }
}