package io.droidevs.taskjournal.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.droidevs.taskjournal.domain.preference.ShowCompletedPreference
import io.droidevs.taskjournal.domain.preference.SortOrderPreference
import io.droidevs.taskjournal.domain.preference.ThemePreference
import io.droidevs.taskjournal.domain.result.Result
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themePreference: ThemePreference,
    private val sortOrderPreference: SortOrderPreference,
    private val showCompletedPreference: ShowCompletedPreference
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            combine(
                themePreference.getTheme(),
                sortOrderPreference.getSortOrder(),
                showCompletedPreference.getShowCompleted()
            ) { themeResult, sortOrderResult, showCompletedResult ->
                Settings(
                    isDarkMode = themeResult.getOrNull() ?: Settings().isDarkMode,
                    sortOrder = sortOrderResult.getOrNull() ?: Settings().sortOrder,
                    showCompleted = showCompletedResult.getOrNull() ?: Settings().showCompleted
                )
            }.collect { settings ->
                _state.update { it.copy(settings = settings, isLoading = false) }
            }
        }
    }

    fun updateTheme(isDarkMode: Boolean) {
        viewModelScope.launch {
            themePreference.setTheme(isDarkMode)
        }
    }

    fun updateSortOrder(sortOrder: SortOrder) {
        viewModelScope.launch {
            sortOrderPreference.setSortOrder(sortOrder)
        }
    }

    fun updateShowCompleted(showCompleted: Boolean) {
        viewModelScope.launch {
            showCompletedPreference.setShowCompleted(showCompleted)
        }
    }
}

data class SettingsState(
    val settings: Settings = Settings(),
    val isLoading: Boolean = false
)

data class Settings(
    val isDarkMode: Boolean = false,
    val sortOrder: SortOrder = SortOrder.CREATED_AT_DESC,
    val showCompleted: Boolean = true
)

enum class SortOrder {
    CREATED_AT_ASC,
    CREATED_AT_DESC,
    TITLE_ASC,
    TITLE_DESC,
    PRIORITY_ASC,
    PRIORITY_DESC
} 
