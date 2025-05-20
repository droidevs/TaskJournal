package io.droidevs.taskjournal.domain.usecase.category

import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.model.Category
import io.droidevs.taskjournal.domain.repository.CategoryRepository
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    operator fun invoke(): Flow<Result<List<Category>, DatabaseError>> = repository.getAllCategories()
} 