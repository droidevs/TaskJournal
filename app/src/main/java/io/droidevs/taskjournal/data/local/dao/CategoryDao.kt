package io.droidevs.taskjournal.data.local.dao

import androidx.room.*
import io.droidevs.taskjournal.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY name ASC LIMIT :limit OFFSET :offset")
    fun getAllCategories(
        offset: Int,
        limit: Int
    ): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE id = :id")
    fun getCategoryById(id: Long): Flow<CategoryEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity): Long

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)

    @Query("DELETE FROM categories WHERE id = :id")
    suspend fun deleteCategoryById(id: Long)

    @Query("SELECT * FROM categories WHERE name LIKE '%' || :query || '%' ORDER BY name ASC LIMIT :limit OFFSET :offset")
    fun searchCategories(
        query: String,
        offset: Int,
        limit: Int
    ): Flow<List<CategoryEntity>>
} 