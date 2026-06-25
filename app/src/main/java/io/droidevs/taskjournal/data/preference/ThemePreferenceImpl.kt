package io.droidevs.taskjournal.data.preference

import io.droidevs.taskjournal.domain.model.ThemeMode
import io.droidevs.taskjournal.domain.preference.AppPreferencesPreference
import io.droidevs.taskjournal.domain.preference.ThemePreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThemePreferenceImpl @Inject constructor(
    private val appPreferences: AppPreferencesPreference
) : ThemePreference {
    override fun getTheme(): Flow<Result<Boolean, PreferenceError>> {
        return appPreferences.getPreferences().map { result ->
            result.fold(
                onSuccess = { Result.Success(it.themeMode == ThemeMode.DARK) },
                onFailure = { Result.Failure(it) }
            )
        }
    }

    override suspend fun setTheme(isDarkMode: Boolean): Result<Unit, PreferenceError> {
        return appPreferences.updatePreferences {
            it.copy(themeMode = if (isDarkMode) ThemeMode.DARK else ThemeMode.LIGHT)
        }
    }
}

