package io.droidevs.taskjournal.data.preference.delegate

import androidx.datastore.core.DataStore
import io.droidevs.taskjournal.data.preference.exceptions.flowCatchingPreference
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import io.droidevs.taskjournal.domain.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface ProtoReadDelegate<D> {

    fun <T> get(
        defaultValue: T,
        read: (D) -> T
    ): Flow<Result<T, PreferenceError>>


}
class ProtoReadDelegateImpl<D>(
    private val dataStore: DataStore<D>
) : ProtoReadDelegate<D> {

    override fun <T> get(
        defaultValue: T,
        read: (D) -> T
    ): Flow<Result<T, PreferenceError>> = flowCatchingPreference {
        dataStore.data.map { read(it) ?: defaultValue }
    }
}
