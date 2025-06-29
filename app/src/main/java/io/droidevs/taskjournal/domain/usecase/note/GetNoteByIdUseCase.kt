package io.droidevs.taskjournal.domain.usecase.note

import io.droidevs.taskjournal.domain.dispatchers.AppDispatchersProvider
import io.droidevs.taskjournal.domain.model.Note
import io.droidevs.taskjournal.domain.repository.NoteRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DataError
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val dispatchers: AppDispatchersProvider
) {
    operator fun invoke(id: Long): Flow<Result<Note?, DatabaseError>> =
        repository.getNoteById(id)
            .flowOn(dispatchers.io)
} 