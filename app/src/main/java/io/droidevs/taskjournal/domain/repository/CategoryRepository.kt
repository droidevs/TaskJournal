package io.droidevs.taskjournal.domain.repository

import io.droidevs.taskjournal.domain.model.Category
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DataError
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getAllCategories(
        page: Int,
        pageSize: Int
    ): Flow<Result<List<Category>, DatabaseError>>
    fun getCategoryById(id: Long): Flow<Result<Category?, DatabaseError>>
    suspend fun insertCategory(category: Category): Result<Long, DatabaseError>
    suspend fun updateCategory(category: Category): Result<Unit, DatabaseError>
    suspend fun deleteCategory(category: Category): Result<Unit, DatabaseError>
    suspend fun deleteCategoryById(id: Long): Result<Unit, DatabaseError>
    fun searchCategories(
        query: String,
        page: Int,
        pageSize: Int
    ): Flow<Result<List<Category>, DatabaseError>>
} 