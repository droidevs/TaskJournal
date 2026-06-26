package io.droidevs.taskjournal.domain.usecase

import io.droidevs.taskjournal.domain.dispatchers.AppDispatchersProvider
import io.droidevs.taskjournal.domain.model.Category
import io.droidevs.taskjournal.domain.repository.CategoryRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DeleteCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository,
    private val dispatchers : AppDispatchersProvider
) {
    suspend operator fun invoke(category: Category): Result<Unit, DatabaseError> = withContext(dispatchers.io){
        repository.deleteCategory(category)
    }

    suspend operator fun invoke(categoryId: Long): Result<Unit, DatabaseError> = withContext(dispatchers.io){
        repository.deleteCategoryById(categoryId)
    }
}

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

class GetCategoryByIdUseCase @Inject constructor(
    private val repository: CategoryRepository,
    private val dispatcherProvider: AppDispatchersProvider
) {
    operator fun invoke(id: Long): Flow<Result<Category?, DatabaseError>> =
        repository.getCategoryById(id).flowOn(dispatcherProvider.io)
}

class InsertCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(category: Category): Result<Long, DatabaseError> = withContext(dispatchers.io){
        repository.insertCategory(category)
    }
}

class UpdateCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(category: Category): Result<Unit, DatabaseError> = withContext(dispatchers.io){
        repository.updateCategory(category)
    }
}

class SearchCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository,
    private val dispatchers: AppDispatchersProvider
) {
    operator fun invoke(
        query: String,
        page: Int = 1,
        pageSize: Int = 20
    ): Flow<Result<List<Category>, DatabaseError>> =
        repository.searchCategories(query = query, page = page, pageSize = pageSize)
            .flowOn(dispatchers.io)
}

data class CategoryUseCases @Inject constructor(
    val getById: GetCategoryByIdUseCase,
    val getAll: GetAllCategoriesUseCase,
    val search: SearchCategoriesUseCase,
    val update: UpdateCategoryUseCase,
    val delete: DeleteCategoryUseCase,
    val add: InsertCategoryUseCase
)