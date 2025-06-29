package io.droidevs.taskjournal.domain.usecase.category

import io.droidevs.taskjournal.domain.dispatchers.AppDispatchersProvider
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.model.Category
import io.droidevs.taskjournal.domain.repository.CategoryRepository
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCategoryByIdUseCase @Inject constructor(
    private val repository: CategoryRepository,
    private val dispatcherProvider: AppDispatchersProvider
) {
    operator fun invoke(id: Long): Flow<Result<Category?, DatabaseError>> =
        repository.getCategoryById(id).flowOn(dispatcherProvider.io)
} 