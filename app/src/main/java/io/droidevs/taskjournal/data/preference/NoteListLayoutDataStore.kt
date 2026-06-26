package io.droidevs.taskjournal.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.droidevs.taskjournal.data.preference.delegate.enumPreferenceDelegate
import io.droidevs.taskjournal.domain.model.NoteListLayout
import io.droidevs.taskjournal.domain.preference.NoteListLayoutPreference
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.PreferenceError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteListLayoutDataStore @Inject constructor(
    dataStore: DataStore<Preferences>
) : NoteListLayoutPreference {

    private val delegate = enumPreferenceDelegate(
        dataStore = dataStore,
        key = PreferenceKeys.NOTE_LIST_LAYOUT,
        defaultValue = NoteListLayout.LIST
    )

    override val value: Flow<Result<NoteListLayout, PreferenceError>> = delegate.flow

    override suspend fun save(value: NoteListLayout): Result<NoteListLayout, PreferenceError> = delegate.set(value)
}
