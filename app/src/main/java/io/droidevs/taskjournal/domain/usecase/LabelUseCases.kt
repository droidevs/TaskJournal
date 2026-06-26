package io.droidevs.taskjournal.domain.usecase

import io.droidevs.taskjournal.domain.dispatchers.AppDispatchersProvider
import io.droidevs.taskjournal.domain.model.Label
import io.droidevs.taskjournal.domain.repository.LabelRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllLabelsUseCase @Inject constructor(
    private val repository: LabelRepository,
    private val dispatchers: AppDispatchersProvider
) {
    operator fun invoke(page: Int = 1, pageSize: Int = 20): Flow<Result<List<Label>, DatabaseError>> =
        repository.getAllLabels(page = page, pageSize = pageSize).flowOn(dispatchers.io)
}

class GetLabelsByUsageUseCase @Inject constructor(
    private val repository: LabelRepository,
    private val dispatchers: AppDispatchersProvider
) {
    operator fun invoke(): Flow<Result<List<Label>, DatabaseError>> =
        repository.getLabelsByUsage().flowOn(dispatchers.io)
}

class GetLabelByIdUseCase @Inject constructor(
    private val repository: LabelRepository,
    private val dispatchers: AppDispatchersProvider
) {
    operator fun invoke(id: Long): Flow<Result<Label?, DatabaseError>> =
        repository.getLabelById(id).flowOn(dispatchers.io)
}

class SearchLabelsUseCase @Inject constructor(
    private val repository: LabelRepository,
    private val dispatchers: AppDispatchersProvider
) {
    operator fun invoke(
        query: String,
        page: Int = 1,
        pageSize: Int = 20
    ): Flow<Result<List<Label>, DatabaseError>> =
        repository.searchLabels(query = query, page = page, pageSize = pageSize).flowOn(dispatchers.io)
}

class InsertLabelUseCase @Inject constructor(
    private val repository: LabelRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(label: Label): Result<Long, DatabaseError> =
        withContext(dispatchers.io) { repository.insertLabel(label) }
}

class UpdateLabelUseCase @Inject constructor(
    private val repository: LabelRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(label: Label): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) { repository.updateLabel(label) }
}

class DeleteLabelUseCase @Inject constructor(
    private val repository: LabelRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(label: Label): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) { repository.deleteLabel(label) }
}

class SetLabelsForNoteUseCase @Inject constructor(
    private val repository: LabelRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(noteId: Long, labelIds: List<Long>): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) { repository.setLabelsForNote(noteId, labelIds) }
}



/**
 * Aggregate of every label-related use case, injected as a single dependency
 * into ViewModels that need label functionality (e.g. label management screen,
 * note editor's label picker).
 */
data class LabelUseCases @Inject constructor(
    val getAll: GetAllLabelsUseCase,
    val getByUsage: GetLabelsByUsageUseCase,
    val getById: GetLabelByIdUseCase,
    val search: SearchLabelsUseCase,
    val insert: InsertLabelUseCase,
    val update: UpdateLabelUseCase,
    val delete: DeleteLabelUseCase,
    val setForNote: SetLabelsForNoteUseCase
)