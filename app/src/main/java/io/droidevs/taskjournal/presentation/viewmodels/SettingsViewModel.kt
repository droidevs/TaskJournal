package io.droidevs.taskjournal.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.droidevs.taskjournal.domain.model.AppPreferences
import io.droidevs.taskjournal.domain.model.SortOrder
import io.droidevs.taskjournal.domain.preference.AppPreferencesPreference
import io.droidevs.taskjournal.domain.preference.ShowCompletedPreference
import io.droidevs.taskjournal.domain.preference.SortOrderPreference
import io.droidevs.taskjournal.domain.preference.ThemePreference
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appPreferencesPreference: AppPreferencesPreference,
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

        viewModelScope.launch {
            appPreferencesPreference.getPreferences().collect { result ->
                result.getOrNull()?.let { preferences ->
                    _state.update { it.copy(preferences = preferences) }
                }
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

    fun updatePreferences(preferences: AppPreferences) {
        viewModelScope.launch {
            appPreferencesPreference.setPreferences(preferences)
        }
    }
}

data class SettingsState(
    val settings: Settings = Settings(),
    val preferences: AppPreferences = AppPreferences(),
    val isLoading: Boolean = false
)

data class Settings(
    val isDarkMode: Boolean = false,
    val sortOrder: SortOrder = SortOrder.CREATED_AT_DESC,
    val showCompleted: Boolean = true
)
