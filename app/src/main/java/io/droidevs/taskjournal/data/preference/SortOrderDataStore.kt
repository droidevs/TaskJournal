package io.droidevs.taskjournal.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.droidevs.taskjournal.data.preference.delegate.enumPreferenceDelegate
import io.droidevs.taskjournal.domain.model.SortOrder
import io.droidevs.taskjournal.domain.preference.SortOrderPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SortOrderDataStore @Inject constructor(
    dataStore: DataStore<Preferences>
) : SortOrderPreference {

    private val delegate = enumPreferenceDelegate(
        dataStore = dataStore,
        key = PreferenceKeys.SORT_ORDER,
        defaultValue = SortOrder.CREATED_AT_DESC
    )

    override val value: Flow<Result<SortOrder, PreferenceError>> = delegate.flow

    override suspend fun save(value: SortOrder): Result<SortOrder, PreferenceError> = delegate.set(value)
}
