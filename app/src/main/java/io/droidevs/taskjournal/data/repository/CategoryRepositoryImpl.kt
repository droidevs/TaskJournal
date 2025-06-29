package io.droidevs.taskjournal.data.repository

import io.droidevs.taskjournal.data.local.dao.CategoryDao
import io.droidevs.taskjournal.data.local.entity.CategoryEntity
import io.droidevs.taskjournal.data.local.exceptions.asDatabaseResultFlow
import io.droidevs.taskjournal.data.local.exceptions.runCatchingDatabaseResult
import io.droidevs.taskjournal.data.mappers.toDomainModel
import io.droidevs.taskjournal.data.mappers.toEntity
import io.droidevs.taskjournal.domain.model.Category
import io.droidevs.taskjournal.domain.repository.CategoryRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DataError
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val dao: CategoryDao
) : CategoryRepository {

    override fun getAllCategories(
        page: Int,
        pageSize: Int
    ): Flow<Result<List<Category>, DatabaseError>> {
        return dao.getAllCategories(offset = (page - 1) * pageSize, limit = pageSize)
            .map { entities ->
                Result.Success(entities.map { it.toDomainModel() })
            }
            .asDatabaseResultFlow()
    }

    override fun getCategoryById(id: Long): Flow<Result<Category?, DatabaseError>> {
        return dao.getCategoryById(id)
            .map { entity ->
                Result.Success(entity?.toDomainModel())
            }
            .asDatabaseResultFlow()
    }

    override suspend fun insertCategory(category: Category): Result<Long, DatabaseError> {
        return runCatchingDatabaseResult {
            dao.insertCategory(category.toEntity())
        }
    }

    override suspend fun updateCategory(category: Category): Result<Unit, DatabaseError> {
        return runCatchingDatabaseResult {
            dao.updateCategory(category.toEntity())
        }
    }

    override suspend fun deleteCategory(category: Category): Result<Unit, DatabaseError> {
        return runCatchingDatabaseResult {
            dao.deleteCategory(category.toEntity())
        }
    }

    override suspend fun deleteCategoryById(id: Long): Result<Unit, DatabaseError> {
        return runCatchingDatabaseResult {
            dao.deleteCategoryById(id)
        }
    }

    override fun searchCategories(
        query: String,
        page: Int,
        pageSize: Int
    ): Flow<Result<List<Category>, DatabaseError>> {
        return dao.searchCategories(query = query, offset = (page - 1) * pageSize, limit = pageSize)
            .map { entities ->
                Result.Success(entities.map { it.toDomainModel() })
            }
            .asDatabaseResultFlow()
    }
} 