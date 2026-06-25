package io.droidevs.taskjournal.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.droidevs.taskjournal.domain.model.Note
import io.droidevs.taskjournal.domain.repository.NoteRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val repository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(NoteDetailState())
    val state: StateFlow<NoteDetailState> = _state.asStateFlow()

    private val noteId: Long = checkNotNull(savedStateHandle["noteId"])

    init {
        loadNote()
    }

    private fun loadNote() {
        viewModelScope.launch {
            repository.getNoteById(noteId).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update { it.copy(note = result.data) }
                    }
                    is Result.Failure -> {
                        _state.update { it.copy(error = result.error) }
                    }
                }
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            when (val result = repository.updateNote(note)) {
                is Result.Success -> {
                    _state.update { it.copy(note = note) }
                }
                is Result.Failure -> {
                    _state.update { it.copy(error = result.error) }
                }
            }
        }
    }

    fun deleteNote() {
        viewModelScope.launch {
            state.value.note?.let { note ->
                when (val result = repository.deleteNote(note)) {
                    is Result.Success -> {
                        _state.update { it.copy(isDeleted = true) }
                    }
                    is Result.Failure -> {
                        _state.update { it.copy(error = result.error) }
                    }
                }
            }
        }
    }

    fun togglePin() {
        viewModelScope.launch {
            state.value.note?.let { note ->
                val updatedNote = note.copy(isPinned = !note.isPinned)
                updateNote(updatedNote)
            }
        }
    }
}

data class NoteDetailState(
    val note: Note? = null,
    val isLoading: Boolean = false,
    val error: DatabaseError? = null,
    val isDeleted: Boolean = false
) 
