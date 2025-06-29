package io.droidevs.taskjournal.domain.usecase.category

import io.droidevs.taskjournal.domain.dispatchers.AppDispatchersProvider
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.model.Category
import io.droidevs.taskjournal.domain.repository.CategoryRepository
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository,
    private val dispatchersProvider: AppDispatchersProvider
) {
    operator fun invoke(
        page: Int,
        pageSize: Int
    ): Flow<Result<List<Category>, DatabaseError>> =
        repository.getAllCategories(page = page, pageSize= pageSize)
            .flowOn(dispatchersProvider.io)
} 