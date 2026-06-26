package io.droidevs.taskjournal.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.droidevs.taskjournal.data.preference.delegate.NullableLongPreferenceDelegate
import io.droidevs.taskjournal.domain.preference.DefaultCategoryPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultCategoryDataStore @Inject constructor(
    dataStore: DataStore<Preferences>
) : DefaultCategoryPreference {

    private val delegate = NullableLongPreferenceDelegate(
        dataStore = dataStore,
        key = PreferenceKeys.DEFAULT_CATEGORY_ID
    )

    override val value: Flow<Result<Long?, PreferenceError>> = delegate.flow

    override suspend fun save(value: Long?): Result<Long?, PreferenceError> = delegate.set(value)
}