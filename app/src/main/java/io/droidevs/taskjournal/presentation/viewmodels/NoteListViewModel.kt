package io.droidevs.taskjournal.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.droidevs.taskjournal.domain.model.Note
import io.droidevs.taskjournal.domain.pager.DefaultPaginator
import io.droidevs.taskjournal.domain.repository.NoteRepository
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NoteListState())
    val state: StateFlow<NoteListState> = _state.asStateFlow()

    private val paginator = DefaultPaginator(
        initialKey = _state.value.page,
        onLoadUpdated = { isLoading ->
            _state.update { it.copy(isLoading = isLoading) }
        },
        onRequest = { nextPage ->
            repository.getAllNotes(
                page = nextPage,
                pageSize = _state.value.pageSize
            ).first()
        },
        getNextKey = { currentPage, _ ->
            currentPage + 1
        },
        onError = { error ->
            _state.update { it.copy(error = error) }
        },
        onSuccess = { items, newKey ->
            _state.update { 
                it.copy(
                    notes = items,
                    page = newKey,
                    endReached = items.isEmpty()
                )
            }
        }
    )

    init {
        loadNotes()
    }

    fun loadNotes() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    fun refresh() {
        viewModelScope.launch {
            paginator.reset()
        }
    }

    fun togglePinnedNotes() {
        _state.update { it.copy(showPinnedOnly = !it.showPinnedOnly) }
        refresh()
    }

    fun searchNotes(query: String) {
        _state.update { it.copy(searchQuery = query) }
        refresh()
    }
}

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val error: DatabaseError? = null,
    val page: Int = 1,
    val pageSize: Int = 20,
    val endReached: Boolean = false,
    val showPinnedOnly: Boolean = false,
    val searchQuery: String = ""
) 
