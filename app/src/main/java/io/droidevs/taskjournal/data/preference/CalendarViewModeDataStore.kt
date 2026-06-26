package io.droidevs.taskjournal.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.droidevs.taskjournal.data.preference.delegate.enumPreferenceDelegate
import io.droidevs.taskjournal.domain.model.CalendarViewMode
import io.droidevs.taskjournal.domain.preference.CalendarViewModePreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarViewModeDataStore @Inject constructor(
    dataStore: DataStore<Preferences>
) : CalendarViewModePreference {

    private val delegate = enumPreferenceDelegate(
        dataStore = dataStore,
        key = PreferenceKeys.CALENDAR_VIEW_MODE,
        defaultValue = CalendarViewMode.MONTH
    )

    override val value: Flow<Result<CalendarViewMode, PreferenceError>> = delegate.flow

    override suspend fun save(value: CalendarViewMode): Result<CalendarViewMode, PreferenceError> = delegate.set(value)
}
