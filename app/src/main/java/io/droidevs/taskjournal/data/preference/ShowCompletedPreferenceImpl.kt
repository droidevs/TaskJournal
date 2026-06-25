package io.droidevs.taskjournal.data.preference

import io.droidevs.taskjournal.domain.preference.AppPreferencesPreference
import io.droidevs.taskjournal.domain.preference.ShowCompletedPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShowCompletedPreferenceImpl @Inject constructor(
    private val appPreferences: AppPreferencesPreference
) : ShowCompletedPreference {
    override fun getShowCompleted(): Flow<Result<Boolean, PreferenceError>> {
        return appPreferences.getPreferences().map { result ->
            result.fold(
                onSuccess = { Result.Success(it.showCompleted) },
                onFailure = { Result.Failure(it) }
            )
        }
    }

    override suspend fun setShowCompleted(showCompleted: Boolean): Result<Unit, PreferenceError> {
        return appPreferences.updatePreferences { it.copy(showCompleted = showCompleted) }
    }
}

