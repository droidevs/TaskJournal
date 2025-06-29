package io.droidevs.taskjournal.domain.usecase.note

import io.droidevs.taskjournal.domain.dispatchers.AppDispatchersProvider
import io.droidevs.taskjournal.domain.model.Note
import io.droidevs.taskjournal.domain.model.NoteOrder
import io.droidevs.taskjournal.domain.repository.NoteRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetDeletedNotesUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val dispatchers: AppDispatchersProvider
) {
    operator fun invoke(
        page: Int,
        pageSize: Int,
        order: NoteOrder
    ): Flow<Result<List<Note>, DatabaseError>> =
        repository.getDeletedNotes(page = page, pageSize = pageSize,order = order)
            .flowOn(dispatchers.io)

}