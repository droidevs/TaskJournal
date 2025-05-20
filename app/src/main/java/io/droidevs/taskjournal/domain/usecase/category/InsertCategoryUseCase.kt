package io.droidevs.taskjournal.domain.usecase.category

import io.droidevs.taskjournal.domain.model.Category
import io.droidevs.taskjournal.domain.repository.CategoryRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DataError
import javax.inject.Inject

class InsertCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(category: Category): Result<Long, DataError.DatabaseError> = repository.insertCategory(category)
} 