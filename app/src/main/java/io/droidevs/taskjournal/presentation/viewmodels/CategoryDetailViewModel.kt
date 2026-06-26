package io.droidevs.taskjournal.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.droidevs.taskjournal.domain.model.Category
import io.droidevs.taskjournal.domain.result.onFailureSuspend
import io.droidevs.taskjournal.domain.result.onSuccessSuspend
import io.droidevs.taskjournal.domain.usecase.CategoryUseCases
import io.droidevs.taskjournal.presentation.core.actions.CategoryDetailScreenAction
import io.droidevs.taskjournal.presentation.core.events.CategoryDetailScreenEvent
import io.droidevs.taskjournal.presentation.core.state.CategoryDetailScreenState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Handles both creating a new category and editing an existing one.
 * `categoryId` arrives via [SavedStateHandle] and is `null`/absent for the "create" flow.
 */
@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val categoryId: Long? = savedStateHandle.get<Long>("categoryId")?.takeIf { it > 0 }

    private val _state = MutableStateFlow(CategoryDetailScreenState(categoryId = categoryId, isNewCategory = categoryId == null))
    val state: StateFlow<CategoryDetailScreenState> = _state.asStateFlow()
        .onStart { if (categoryId != null) loadCategory(categoryId) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CategoryDetailScreenState(categoryId = categoryId, isNewCategory = categoryId == null)
        )

    private val _events = MutableSharedFlow<CategoryDetailScreenEvent>()
    val events: SharedFlow<CategoryDetailScreenEvent> = _events.asSharedFlow()

    private fun loadCategory(id: Long) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            categoryUseCases.getById(id).collect { result ->
                result.onSuccessSuspend { category ->
                    if (category != null) {
                        _state.update {
                            it.copy(
                                name = category.name,
                                colorHex = String.format("#%06X", 0xFFFFFF and category.color),
                                isLoading = false
                            )
                        }
                    } else {
                        _state.update { it.copy(isLoading = false) }
                    }
                }.onFailureSuspend { error ->
                    _state.update { it.copy(error = error, isLoading = false) }
                }
            }
        }
    }

    private fun saveCategory() {
        val current = _state.value
        if (current.name.isBlank()) {
            viewModelScope.launch { _events.emit(CategoryDetailScreenEvent.ValidationNameRequired) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            val colorInt = runCatching { android.graphics.Color.parseColor(current.colorHex) }
                .getOrDefault(0xFF6750A4.toInt())

            val category = Category(
                id = current.categoryId ?: 0,
                name = current.name.trim(),
                description = "",
                color = colorInt
            )

            if (current.isNewCategory) {
                categoryUseCases.add(category)
                    .onSuccessSuspend { onSaveSuccess() }
                    .onFailureSuspend { onSaveFailure() }
            } else {
                categoryUseCases.update(category)
                    .onSuccessSuspend { onSaveSuccess() }
                    .onFailureSuspend { onSaveFailure() }
            }
        }
    }

    private suspend fun onSaveSuccess() {
        _state.update { it.copy(isSaving = false) }
        _events.emit(CategoryDetailScreenEvent.CategorySavedSuccessfully)
        _events.emit(CategoryDetailScreenEvent.NavigateBack)
    }

    private suspend fun onSaveFailure() {
        _state.update { it.copy(isSaving = false) }
        _events.emit(CategoryDetailScreenEvent.CategorySaveFailed)
    }

    private fun deleteCategory() {
        val id = _state.value.categoryId ?: return
        viewModelScope.launch {
            categoryUseCases.delete(id)
                .onSuccessSuspend {
                    _events.emit(CategoryDetailScreenEvent.CategoryDeletedSuccessfully)
                    _events.emit(CategoryDetailScreenEvent.NavigateBack)
                }
                .onFailureSuspend { _events.emit(CategoryDetailScreenEvent.CategoryDeleteFailed) }
        }
    }

    fun onAction(action: CategoryDetailScreenAction) {
        when (action) {
            is CategoryDetailScreenAction.NameChanged -> _state.update { it.copy(name = action.name) }
            is CategoryDetailScreenAction.ColorChanged -> _state.update { it.copy(colorHex = action.colorHex) }
            is CategoryDetailScreenAction.IconChanged -> _state.update { it.copy(iconKey = action.iconKey) }
            CategoryDetailScreenAction.SaveCategory -> saveCategory()
            CategoryDetailScreenAction.DeleteCategory -> deleteCategory()
            CategoryDetailScreenAction.NavigateBack -> {
                viewModelScope.launch { _events.emit(CategoryDetailScreenEvent.NavigateBack) }
            }
        }
    }
}