package io.droidevs.taskjournal.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.droidevs.taskjournal.domain.pager.DefaultPaginator
import io.droidevs.taskjournal.domain.result.onFailureSuspend
import io.droidevs.taskjournal.domain.result.onSuccessSuspend
import io.droidevs.taskjournal.domain.usecase.CategoryUseCases
import io.droidevs.taskjournal.presentation.core.actions.CategoryListScreenAction
import io.droidevs.taskjournal.presentation.core.events.CategoryListScreenEvent
import io.droidevs.taskjournal.presentation.core.state.CategoryListScreenState
import io.droidevs.taskjournal.presentation.models.mappers.toUiModel
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
class CategoryListViewModel @Inject constructor(
    private val categoryUseCases: CategoryUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(CategoryListScreenState())
    val state: StateFlow<CategoryListScreenState> = _state.asStateFlow()
        .onStart { loadMoreCategories() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CategoryListScreenState()
        )

    private val _events = MutableSharedFlow<CategoryListScreenEvent>()
    val events: SharedFlow<CategoryListScreenEvent> = _events.asSharedFlow()

    private val paginator = DefaultPaginator(
        initialKey = 1,
        onLoadUpdated = { isLoading -> _state.update { it.copy(isLoading = isLoading) } },
        getNextKey = { currentKey, _ -> currentKey + 1 },
        onRequest = { nextPage ->
            val query = state.value.searchQuery
            if (query.isBlank()) {
                categoryUseCases.getAll(page = nextPage, pageSize = state.value.pageSize).first()
            } else {
                categoryUseCases.search(query = query, page = nextPage, pageSize = state.value.pageSize).first()
            }
        },
        onError = { error -> _state.update { it.copy(error = error) } },
        onSuccess = { categories, newPage ->
            _state.update {
                val uiCategories = categories.map { it.toUiModel() }
                it.copy(
                    categories = if (newPage == 2) uiCategories else it.categories + uiCategories,
                    page = newPage,
                    endReached = categories.isEmpty()
                )
            }
        }
    )

    private fun loadMoreCategories() {
        if (_state.value.isLoading || _state.value.endReached) return
        viewModelScope.launch { paginator.loadNextItems() }
    }

    private fun refresh() {
        viewModelScope.launch {
            _state.update { it.copy(categories = emptyList(), endReached = false, page = 1) }
            paginator.reset()
        }
    }

    private fun search(query: String) {
        _state.update { it.copy(searchQuery = query, categories = emptyList(), endReached = false, page = 1) }
        refresh()
    }

    private fun deleteCategory(categoryId: Long) {
        viewModelScope.launch {
            categoryUseCases.delete(categoryId)
                .onSuccessSuspend {
                    _state.update { current -> current.copy(categories = current.categories.filterNot { it.id == categoryId }) }
                    _events.emit(CategoryListScreenEvent.CategoryDeletedSuccessfully)
                }
                .onFailureSuspend { _events.emit(CategoryListScreenEvent.CategoryDeleteFailed) }
        }
    }

    fun onAction(action: CategoryListScreenAction) {
        when (action) {
            CategoryListScreenAction.LoadMoreCategories -> loadMoreCategories()
            CategoryListScreenAction.RefreshCategories -> refresh()
            is CategoryListScreenAction.Search -> search(action.query)
            is CategoryListScreenAction.OpenCategory -> {
                viewModelScope.launch { _events.emit(CategoryListScreenEvent.NavigateToCategoryDetail(action.categoryId)) }
            }
            CategoryListScreenAction.CreateCategory -> {
                viewModelScope.launch { _events.emit(CategoryListScreenEvent.NavigateToCategoryCreate) }
            }
            is CategoryListScreenAction.EditCategory -> {
                viewModelScope.launch { _events.emit(CategoryListScreenEvent.NavigateToCategoryEdit(action.categoryId)) }
            }
            is CategoryListScreenAction.DeleteCategory -> deleteCategory(action.categoryId)
            CategoryListScreenAction.NavigateBack -> {
                viewModelScope.launch { _events.emit(CategoryListScreenEvent.NavigateBack) }
            }
        }
    }
}