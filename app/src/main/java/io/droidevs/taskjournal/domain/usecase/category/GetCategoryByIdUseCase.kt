package io.droidevs.taskjournal.domain.usecase.category

import io.droidevs.taskjournal.domain.dispatchers.CoroutineDispatcherProvider
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.model.Category
import io.droidevs.taskjournal.domain.repository.CategoryRepository
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCategoryByIdUseCase @Inject constructor(
    private val repository: CategoryRepository,
    private val dispatcherProvider: CoroutineDispatcherProvider
) {
    suspend operator fun invoke(id: Long): Flow<Result<Category?, DatabaseError>> =
        withContext(dispatcherProvider.io){
            repository.getCategoryById(id)
        }
} 