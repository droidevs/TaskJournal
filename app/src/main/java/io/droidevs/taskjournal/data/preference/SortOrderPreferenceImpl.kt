package io.droidevs.taskjournal.data.preference

import io.droidevs.taskjournal.domain.model.SortOrder
import io.droidevs.taskjournal.domain.preference.AppPreferencesPreference
import io.droidevs.taskjournal.domain.preference.SortOrderPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SortOrderPreferenceImpl @Inject constructor(
    private val appPreferences: AppPreferencesPreference
) : SortOrderPreference {
    override fun getSortOrder(): Flow<Result<SortOrder, PreferenceError>> {
        return appPreferences.getPreferences().map { result ->
            result.fold(
                onSuccess = { Result.Success(it.sortOrder) },
                onFailure = { Result.Failure(it) }
            )
        }
    }

    override suspend fun setSortOrder(sortOrder: SortOrder): Result<Unit, PreferenceError> {
        return appPreferences.updatePreferences { it.copy(sortOrder = sortOrder) }
    }
}

