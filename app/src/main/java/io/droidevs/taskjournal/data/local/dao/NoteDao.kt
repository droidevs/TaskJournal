package io.droidevs.taskjournal.data.local.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import io.droidevs.taskjournal.data.local.entity.CategoryEntity
import io.droidevs.taskjournal.data.local.entity.NoteEntity
import io.droidevs.taskjournal.data.local.entity.NoteWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Transaction
    @RawQuery(observedEntities = [NoteEntity::class, CategoryEntity::class])
    fun observeNotes(query: SupportSQLiteQuery): Flow<List<NoteWithCategory>>

    @Transaction
    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Long): Flow<NoteWithCategory?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteNoteById(id: Long)

    @Query("DELETE FROM notes WHERE category_id = :categoryId")
    suspend fun deleteNotesByCategory(categoryId: Long)
}
