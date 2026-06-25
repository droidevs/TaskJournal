package io.droidevs.taskjournal.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import io.droidevs.taskjournal.data.preference.exceptions.flowCatchingPreference
import io.droidevs.taskjournal.data.preference.exceptions.runCatchingPreference
import io.droidevs.taskjournal.domain.preference.SortOrderPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import io.droidevs.taskjournal.presentation.viewmodels.SortOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sort_order_preferences")

class SortOrderPreferenceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SortOrderPreference {

    private object PreferencesKeys {
        val SORT_ORDER = stringPreferencesKey("sort_order")
    }

    override fun getSortOrder(): Flow<Result<SortOrder, PreferenceError>> {
        return flowCatchingPreference {
            context.dataStore.data.map { preferences ->
                preferences[PreferencesKeys.SORT_ORDER]?.let { SortOrder.valueOf(it) }
                    ?: SortOrder.CREATED_AT_DESC
            }
        }
    }

    override suspend fun setSortOrder(sortOrder: SortOrder): Result<Unit, PreferenceError> {
        return runCatchingPreference {
            context.dataStore.edit { preferences ->
                preferences[PreferencesKeys.SORT_ORDER] = sortOrder.name
            }
        }
    }
} 
