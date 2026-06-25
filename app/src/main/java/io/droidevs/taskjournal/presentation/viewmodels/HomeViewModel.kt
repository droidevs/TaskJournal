package io.droidevs.taskjournal.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.droidevs.taskjournal.domain.model.Category
import io.droidevs.taskjournal.domain.model.Note
import io.droidevs.taskjournal.domain.repository.CategoryRepository
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
class HomeViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            // Load pinned notes
            noteRepository.getPinnedNotes().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update { it.copy(pinnedNotes = result.data) }
                    }
                    is Result.Failure -> {
                        _state.update { it.copy(error = result.error) }
                    }
                }
            }
        }

        viewModelScope.launch {
            // Load categories
            categoryRepository.getAllCategories().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update { it.copy(categories = result.data) }
                    }
                    is Result.Failure -> {
                        _state.update { it.copy(error = result.error) }
                    }
                }
            }
        }
    }

    fun refresh() {
        loadData()
    }
}

data class HomeState(
    val pinnedNotes: List<Note> = emptyList(),
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val error: DatabaseError? = null
) 
