package io.droidevs.taskjournal.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import io.droidevs.taskjournal.domain.result.onFailure
import io.droidevs.taskjournal.domain.result.onFailureSuspend
import io.droidevs.taskjournal.domain.result.onSuccess
import io.droidevs.taskjournal.domain.result.onSuccessSuspend
import io.droidevs.taskjournal.domain.usecase.category.CategoryUseCases
import io.droidevs.taskjournal.presentation.core.actions.CategoryDetailsScreenAction
import io.droidevs.taskjournal.presentation.core.events.CategoryDetailsScreenEvent
import io.droidevs.taskjournal.presentation.core.state.CategoryDetailsScreenState
import io.droidevs.taskjournal.presentation.models.mappers.toUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    private val useCases : CategoryUseCases,
    private val appCoroutine: CoroutineScope,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val categoryId: Long = checkNotNull(savedStateHandle["categoryId"])


    private val _state = MutableStateFlow(CategoryDetailsScreenState())
    val state: StateFlow<CategoryDetailsScreenState> =
        _state.asStateFlow()
            .onStart { loadState() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = CategoryDetailsScreenState()
            )

    private val _events = MutableSharedFlow<CategoryDetailsScreenEvent>(
        replay = 3,
        extraBufferCapacity = 10
    )
    val events = _events.asSharedFlow()

    private fun loadState() {
        viewModelScope.launch {
            useCases.getById(categoryId).collect { result ->
                result.onSuccess { category ->
                    _state.update { it.copy(category = category?.toUiModel()) }
                }.onFailure { error ->
                    _state.update { it.copy(error = error) }
                }
            }
        }
    }


    fun onAction(action : CategoryDetailsScreenAction){
        appCoroutine.launch {
            when(action){
                is CategoryDetailsScreenAction.DeleteCategory -> {
                    useCases.delete(categoryId).onSuccessSuspend {
                        _events.emit(CategoryDetailsScreenEvent.DeletedSuccessfully)
                        _events.emit(CategoryDetailsScreenEvent.NavigateBack)
                    }.onFailureSuspend {
                        _events.emit(CategoryDetailsScreenEvent.DeleteFailed)
                    }
                }
                is CategoryDetailsScreenAction.UpdateCategory -> {
                    _events.emit(CategoryDetailsScreenEvent.NavigateToEditScreen)
                }
            }
        }
    }
}
