package io.droidevs.taskjournal.domain.usecase.note

import io.droidevs.taskjournal.domain.dispatchers.AppDispatchersProvider
import io.droidevs.taskjournal.domain.model.NoteOrder
import io.droidevs.taskjournal.domain.repository.NoteRepository
import kotlinx.coroutines.flow.flowOn

class GetNotesByCategoryUseCase(
    private val noteRepository: NoteRepository,
    private val dispatchers: AppDispatchersProvider
) {

    operator fun invoke(
        category: Long,
        page: Int,
        pageSize: Int,
        order : NoteOrder
    ) = noteRepository.getNotesByCategory(
        categoryId = category,
        page = page,
        pageSize = pageSize,
        order = order
    ).flowOn(dispatchers.io)

}
