package io.droidevs.taskjournal.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import io.droidevs.taskjournal.domain.result.onFailureSuspend
import io.droidevs.taskjournal.domain.result.onSuccessSuspend
import io.droidevs.taskjournal.domain.usecase.preference.SettingsUseCases
import io.droidevs.taskjournal.presentation.core.actions.SettingsScreenAction
import io.droidevs.taskjournal.presentation.core.events.SettingsScreenEvent
import io.droidevs.taskjournal.presentation.core.state.SettingsScreenState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsUseCases: SettingsUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsScreenState())
    val state: StateFlow<SettingsScreenState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<SettingsScreenEvent>()
    val events: SharedFlow<SettingsScreenEvent> = _events.asSharedFlow()

    init {
        observeAppearance()
        observeNotesBehavior()
        observeSystem()
        observeDateTime()
    }

    private fun observeAppearance() {
        viewModelScope.launch {
            settingsUseCases.appearance.getThemeMode().collect { result ->
                result.onSuccessSuspend { mode -> _state.update { it.copy(themeMode = mode, isLoading = false) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.appearance.getDynamicColor().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(dynamicColor = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.appearance.getUseAmoledBlack().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(useAmoledBlack = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.appearance.getStartScreen().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(startScreen = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.appearance.getNoteListLayout().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(noteListLayout = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.appearance.getCalendarViewMode().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(calendarViewMode = value) } }
            }
        }
    }

    private fun observeNotesBehavior() {
        viewModelScope.launch {
            settingsUseCases.notes.getSortOrder().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(sortOrder = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.notes.getShowCompleted().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(showCompleted = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.notes.getShowArchived().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(showArchived = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.notes.getShowPinnedFirst().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(showPinnedFirst = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.notes.getCompactMode().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(compactMode = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.notes.getShowWordCount().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(showWordCount = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.notes.getAutoSaveDrafts().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(autoSaveDrafts = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.notes.getConfirmBeforeDelete().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(confirmBeforeDelete = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.notes.getKeepDeletedDays().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(keepDeletedDays = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.notes.getDefaultCategory().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(defaultCategoryId = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.notes.getDefaultPriority().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(defaultPriority = value) } }
            }
        }
    }

    private fun observeSystem() {
        viewModelScope.launch {
            settingsUseCases.system.getNotificationsEnabled().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(notificationsEnabled = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.system.getReminderLeadMinutes().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(reminderLeadMinutes = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.system.getReminderSoundEnabled().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(reminderSoundEnabled = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.system.getReminderVibrationEnabled().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(reminderVibrationEnabled = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.system.getBiometricLockEnabled().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(biometricLockEnabled = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.system.getAutoLockMinutes().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(autoLockMinutes = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.system.getBackupEnabled().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(backupEnabled = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.system.getBackupOnlyOnWifi().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(backupOnlyOnWifi = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.system.getBackupLocation().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(backupLocation = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.system.getSyncEnabled().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(syncEnabled = value) } }
            }
        }
    }

    private fun observeDateTime() {
        viewModelScope.launch {
            settingsUseCases.dateTime.getWeekStart().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(weekStartsOn = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.dateTime.getUse24HourTime().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(use24HourTime = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.dateTime.getDateFormatPattern().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(dateFormatPattern = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.dateTime.getMaxFrequency().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(maxFrequency = value) } }
            }
        }
        viewModelScope.launch {
            settingsUseCases.dateTime.getMaxEndCount().collect { result ->
                result.onSuccessSuspend { value -> _state.update { it.copy(maxEndCount = value) } }
            }
        }
    }

    private fun <T> save(action: suspend () -> Result<T, PreferenceError>) {
        viewModelScope.launch {
            action()
                .onSuccessSuspend { _events.emit(SettingsScreenEvent.SettingSavedSuccessfully) }
                .onFailureSuspend { _events.emit(SettingsScreenEvent.SettingSaveFailed) }
        }
    }

    fun onAction(action: SettingsScreenAction) {
        when (action) {
            // Appearance
            is SettingsScreenAction.SetThemeMode -> save { settingsUseCases.appearance.setThemeMode(action.mode) }
            is SettingsScreenAction.SetDynamicColor -> save { settingsUseCases.appearance.setDynamicColor(action.enabled) }
            is SettingsScreenAction.SetUseAmoledBlack -> save { settingsUseCases.appearance.setUseAmoledBlack(action.enabled) }
            is SettingsScreenAction.SetStartScreen -> save { settingsUseCases.appearance.setStartScreen(action.screen) }
            is SettingsScreenAction.SetNoteListLayout -> save { settingsUseCases.appearance.setNoteListLayout(action.layout) }
            is SettingsScreenAction.SetCalendarViewMode -> save { settingsUseCases.appearance.setCalendarViewMode(action.mode) }

            // Notes behavior
            is SettingsScreenAction.SetSortOrder -> save { settingsUseCases.notes.setSortOrder(action.order) }
            is SettingsScreenAction.SetShowCompleted -> save { settingsUseCases.notes.setShowCompleted(action.show) }
            is SettingsScreenAction.SetShowArchived -> save { settingsUseCases.notes.setShowArchived(action.show) }
            is SettingsScreenAction.SetShowPinnedFirst -> save { settingsUseCases.notes.setShowPinnedFirst(action.show) }
            is SettingsScreenAction.SetCompactMode -> save { settingsUseCases.notes.setCompactMode(action.enabled) }
            is SettingsScreenAction.SetShowWordCount -> save { settingsUseCases.notes.setShowWordCount(action.show) }
            is SettingsScreenAction.SetAutoSaveDrafts -> save { settingsUseCases.notes.setAutoSaveDrafts(action.enabled) }
            is SettingsScreenAction.SetConfirmBeforeDelete -> save { settingsUseCases.notes.setConfirmBeforeDelete(action.enabled) }
            is SettingsScreenAction.SetKeepDeletedDays -> save { settingsUseCases.notes.setKeepDeletedDays(action.days) }
            is SettingsScreenAction.SetDefaultCategory -> save { settingsUseCases.notes.setDefaultCategory(action.categoryId) }
            is SettingsScreenAction.SetDefaultPriority -> save { settingsUseCases.notes.setDefaultPriority(action.priority) }

            // System
            is SettingsScreenAction.SetNotificationsEnabled -> save { settingsUseCases.system.setNotificationsEnabled(action.enabled) }
            is SettingsScreenAction.SetReminderLeadMinutes -> save { settingsUseCases.system.setReminderLeadMinutes(action.minutes) }
            is SettingsScreenAction.SetReminderSoundEnabled -> save { settingsUseCases.system.setReminderSoundEnabled(action.enabled) }
            is SettingsScreenAction.SetReminderVibrationEnabled -> save { settingsUseCases.system.setReminderVibrationEnabled(action.enabled) }
            is SettingsScreenAction.SetBiometricLockEnabled -> save { settingsUseCases.system.setBiometricLockEnabled(action.enabled) }
            is SettingsScreenAction.SetAutoLockMinutes -> save { settingsUseCases.system.setAutoLockMinutes(action.minutes) }
            is SettingsScreenAction.SetBackupEnabled -> save { settingsUseCases.system.setBackupEnabled(action.enabled) }
            is SettingsScreenAction.SetBackupOnlyOnWifi -> save { settingsUseCases.system.setBackupOnlyOnWifi(action.enabled) }
            is SettingsScreenAction.SetBackupLocation -> save { settingsUseCases.system.setBackupLocation(action.location) }
            is SettingsScreenAction.SetSyncEnabled -> save { settingsUseCases.system.setSyncEnabled(action.enabled) }

            // Date & time / recurrence
            is SettingsScreenAction.SetWeekStart -> save { settingsUseCases.dateTime.setWeekStart(action.weekStart) }
            is SettingsScreenAction.SetUse24HourTime -> save { settingsUseCases.dateTime.setUse24HourTime(action.use24Hour) }
            is SettingsScreenAction.SetDateFormatPattern -> save { settingsUseCases.dateTime.setDateFormatPattern(action.pattern) }
            is SettingsScreenAction.SetMaxFrequency -> save { settingsUseCases.dateTime.setMaxFrequency(action.value) }
            is SettingsScreenAction.SetMaxEndCount -> save { settingsUseCases.dateTime.setMaxEndCount(action.value) }

            SettingsScreenAction.NavigateBack -> {
                viewModelScope.launch { _events.emit(SettingsScreenEvent.NavigateBack) }
            }
        }
    }
}