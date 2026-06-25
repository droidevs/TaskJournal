package io.droidevs.taskjournal.data.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import io.droidevs.taskjournal.data.preference.exceptions.flowCatchingPreference
import io.droidevs.taskjournal.data.preference.exceptions.runCatchingPreference
import io.droidevs.taskjournal.domain.preference.ShowCompletedPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "show_completed_preferences")

class ShowCompletedPreferenceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ShowCompletedPreference {

    private object PreferencesKeys {
        val SHOW_COMPLETED = booleanPreferencesKey("show_completed")
    }

    override fun getShowCompleted(): Flow<Result<Boolean, PreferenceError>> {
        return flowCatchingPreference {
            context.dataStore.data.map { preferences ->
                preferences[PreferencesKeys.SHOW_COMPLETED] ?: true
            }
        }
    }

    override suspend fun setShowCompleted(showCompleted: Boolean): Result<Unit, PreferenceError> {
        return runCatchingPreference {
            context.dataStore.edit { preferences ->
                preferences[PreferencesKeys.SHOW_COMPLETED] = showCompleted
            }
        }
    }
} 
