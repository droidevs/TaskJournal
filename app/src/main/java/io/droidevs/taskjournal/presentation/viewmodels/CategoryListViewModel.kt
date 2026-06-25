package io.droidevs.taskjournal.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.droidevs.taskjournal.domain.model.Category
import io.droidevs.taskjournal.domain.pager.DefaultPaginator
import io.droidevs.taskjournal.domain.repository.CategoryRepository
import io.droidevs.taskjournal.presentation.core.actions.CategoryListScreenAction
import io.droidevs.taskjournal.presentation.core.state.CategoryListScreenState
import io.droidevs.taskjournal.presentation.models.mappers.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val repository: CategoryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CategoryListScreenState())
    val state: StateFlow<CategoryListScreenState> = _state.asStateFlow()
        .onStart { loadState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CategoryListScreenState()
        )


    private fun loadState(){
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private val paginator = DefaultPaginator<Int, Category>(
        initialKey = _state.value.page,
        onLoadUpdated = { isLoading ->
            _state.update { it.copy(isLoading = isLoading) }
        },
        onRequest = { nextPage ->
            repository.getAllCategories(
                page = nextPage,
                pageSize = _state.value.pageSize
            ).first()
        },
        getNextKey = { currentPage,_ ->
            currentPage + 1
        },
        onError = { error ->
            _state.update { it.copy(error = error) }
        },
        onSuccess = { items, newKey ->
            _state.update { 
                it.copy(
                    categories = items.map { category -> category.toUiModel() },
                    page = newKey,
                    endReached = items.isEmpty()
                )
            }
        }
    )


    fun onAction(action: CategoryListScreenAction){
        when(action){
            CategoryListScreenAction.DeselectAllCategories -> {

            }
            is CategoryListScreenAction.DeselectCategory -> TODO()
            CategoryListScreenAction.LoadMoreCategories -> TODO()
            CategoryListScreenAction.RefreshCategories -> TODO()
            CategoryListScreenAction.SelectAllCategories -> TODO()
            is CategoryListScreenAction.SelectCategory -> TODO()
        }
    }

    fun refresh() {
        viewModelScope.launch {
            paginator.reset()
        }
    }
}
