package io.droidevs.taskjournal.domain.usecase.note

import io.droidevs.taskjournal.domain.dispatchers.AppDispatchersProvider
import io.droidevs.taskjournal.domain.model.Note
import io.droidevs.taskjournal.domain.repository.NoteRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DataError
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(note: Note): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) {
            repository.updateNote(note)
        }
} 