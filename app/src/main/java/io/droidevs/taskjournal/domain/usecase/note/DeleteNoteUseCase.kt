package io.droidevs.taskjournal.domain.usecase.note

import io.droidevs.taskjournal.domain.model.Note
import io.droidevs.taskjournal.domain.repository.NoteRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DataError
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note): Result<Unit, DataError.DatabaseError> = repository.deleteNote(note)
} 