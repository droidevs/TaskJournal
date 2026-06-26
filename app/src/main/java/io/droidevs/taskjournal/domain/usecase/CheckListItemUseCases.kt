package io.droidevs.taskjournal.domain.usecase

import io.droidevs.taskjournal.domain.dispatchers.AppDispatchersProvider
import io.droidevs.taskjournal.domain.model.ChecklistItem
import io.droidevs.taskjournal.domain.repository.ChecklistItemRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetChecklistItemsForNoteUseCase @Inject constructor(
    private val repository: ChecklistItemRepository,
    private val dispatchers: AppDispatchersProvider
) {
    operator fun invoke(noteId: Long): Flow<Result<List<ChecklistItem>, DatabaseError>> =
        repository.getChecklistItemsForNote(noteId).flowOn(dispatchers.io)
}

class InsertChecklistItemUseCase @Inject constructor(
    private val repository: ChecklistItemRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(item: ChecklistItem): Result<Long, DatabaseError> =
        withContext(dispatchers.io) { repository.insertChecklistItem(item) }
}

class UpdateChecklistItemUseCase @Inject constructor(
    private val repository: ChecklistItemRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(item: ChecklistItem): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) { repository.updateChecklistItem(item) }
}

class ToggleChecklistItemUseCase @Inject constructor(
    private val repository: ChecklistItemRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(item: ChecklistItem): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) {
            repository.updateChecklistItem(item.copy(isChecked = !item.isChecked))
        }
}

class DeleteChecklistItemUseCase @Inject constructor(
    private val repository: ChecklistItemRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(item: ChecklistItem): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) { repository.deleteChecklistItem(item) }

    suspend operator fun invoke(id: Long): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) { repository.deleteChecklistItemById(id) }
}

data class ChecklistItemUseCases @Inject constructor(
    val getForNote: GetChecklistItemsForNoteUseCase,
    val insert: InsertChecklistItemUseCase,
    val update: UpdateChecklistItemUseCase,
    val toggle: ToggleChecklistItemUseCase,
    val delete: DeleteChecklistItemUseCase
)