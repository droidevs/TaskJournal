package io.droidevs.taskjournal.domain.usecase.category

import io.droidevs.taskjournal.domain.dispatchers.AppDispatchersProvider
import io.droidevs.taskjournal.domain.model.Category
import io.droidevs.taskjournal.domain.repository.CategoryRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DataError
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(category: Category): Result<Long, DatabaseError> = withContext(dispatchers.io){
        repository.insertCategory(category)
    }
} 