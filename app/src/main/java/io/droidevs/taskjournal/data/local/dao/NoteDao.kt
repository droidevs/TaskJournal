package io.droidevs.taskjournal.data.local.dao

import androidx.room.*
import io.droidevs.taskjournal.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY updated_at DESC LIMIT :limit OFFSET :offset")
    fun getAllNotes(
        offset: Int,
        limit: Int
    ): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Long): Flow<NoteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun deleteNoteById(id: Long)

    @Query("SELECT * FROM notes WHERE is_pinned = 1 ORDER BY updated_at DESC LIMIT :limit OFFSET :offset")
    fun getPinnedNotes(
        offset: Int,
        limit: Int
    ): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' ORDER BY updated_at DESC LIMIT :limit OFFSET :offset")
    fun searchNotes(
        query: String,
        offset: Int,
        limit: Int
    ): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE is_deleted = 1 ORDER BY created_at DESC LIMIT :limit OFFSET :offset")
    fun getAllDeletedNotes(
        offset: Int,
        limit: Int
    ): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE category_id = :categoryId AND is_deleted = 0 ORDER BY created_at DESC LIMIT :limit OFFSET :offset")
    fun getNotesByCategory(
        categoryId: Long,
        offset: Int,
        limit: Int
    ): Flow<List<NoteEntity>>

    @Query("DELETE FROM notes WHERE category_id = :categoryId")
    suspend fun deleteNotesByCategory(categoryId: Long)

    // Get all notes, sorted by Title
    @Query("SELECT * FROM notes WHERE is_deleted = 0 ORDER BY title ASC LIMIT :limit OFFSET :offset")
    fun getAllNotesSortedByTitleAsc(offset: Int, limit: Int): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE is_deleted = 0 ORDER BY title DESC LIMIT :limit OFFSET :offset")
    fun getAllNotesSortedByTitleDesc(offset: Int, limit: Int): Flow<List<NoteEntity>>

    // Get all notes, sorted by Date (using updated_at)
    @Query("SELECT * FROM notes WHERE is_deleted = 0 ORDER BY updated_at ASC LIMIT :limit OFFSET :offset")
    fun getAllNotesSortedByDateAsc(offset: Int, limit: Int): Flow<List<NoteEntity>>

    // The original getAllNotes is already sorted by date descending, but we add this for clarity
    @Query("SELECT * FROM notes WHERE is_deleted = 0 ORDER BY updated_at DESC LIMIT :limit OFFSET :offset")
    fun getAllNotesSortedByDateDesc(offset: Int, limit: Int): Flow<List<NoteEntity>>


    // Search notes, sorted by Title
    @Query("SELECT * FROM notes WHERE (title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%') AND is_deleted = 0 ORDER BY title ASC LIMIT :limit OFFSET :offset")
    fun searchNotesSortedByTitleAsc(query: String, offset: Int, limit: Int): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE (title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%') AND is_deleted = 0 ORDER BY title DESC LIMIT :limit OFFSET :offset")
    fun searchNotesSortedByTitleDesc(query: String, offset: Int, limit: Int): Flow<List<NoteEntity>>

    // Search notes, sorted by Date
    @Query("SELECT * FROM notes WHERE (title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%') AND is_deleted = 0 ORDER BY updated_at ASC LIMIT :limit OFFSET :offset")
    fun searchNotesSortedByDateAsc(query: String, offset: Int, limit: Int): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE (title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%') AND is_deleted = 0 ORDER BY updated_at DESC LIMIT :limit OFFSET :offset")
    fun searchNotesSortedByDateDesc(query: String, offset: Int, limit: Int): Flow<List<NoteEntity>>


    @Query("SELECT * FROM notes WHERE is_pinned = 1 AND is_deleted = 0 ORDER BY title ASC LIMIT :limit OFFSET :offset")
    fun getPinnedNotesSortedByTitleAsc(offset: Int, limit: Int): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE is_pinned = 1 AND is_deleted = 0 ORDER BY title DESC LIMIT :limit OFFSET :offset")
    fun getPinnedNotesSortedByTitleDesc(offset: Int, limit: Int): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE is_pinned = 1 AND is_deleted = 0 ORDER BY updated_at ASC LIMIT :limit OFFSET :offset")
    fun getPinnedNotesSortedByDateAsc(offset: Int, limit: Int): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE is_pinned = 1 AND is_deleted = 0 ORDER BY updated_at DESC LIMIT :limit OFFSET :offset")
    fun getPinnedNotesSortedByDateDesc(offset: Int, limit: Int): Flow<List<NoteEntity>>


    // --- Get Deleted Notes (with sorting) ---
    @Query("SELECT * FROM notes WHERE is_deleted = 1 ORDER BY title ASC LIMIT :limit OFFSET :offset")
    fun getDeletedNotesSortedByTitleAsc(offset: Int, limit: Int): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE is_deleted = 1 ORDER BY title DESC LIMIT :limit OFFSET :offset")
    fun getDeletedNotesSortedByTitleDesc(offset: Int, limit: Int): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE is_deleted = 1 ORDER BY updated_at ASC LIMIT :limit OFFSET :offset")
    fun getDeletedNotesSortedByDateAsc(offset: Int, limit: Int): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE is_deleted = 1 ORDER BY updated_at DESC LIMIT :limit OFFSET :offset")
    fun getDeletedNotesSortedByDateDesc(offset: Int, limit: Int): Flow<List<NoteEntity>>


    // --- Get Notes by Category (with sorting) ---
    @Query("SELECT * FROM notes WHERE category_id = :categoryId AND is_deleted = 0 ORDER BY title ASC LIMIT :limit OFFSET :offset")
    fun getNotesByCategorySortedByTitleAsc(categoryId: Long, offset: Int, limit: Int): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE category_id = :categoryId AND is_deleted = 0 ORDER BY title DESC LIMIT :limit OFFSET :offset")
    fun getNotesByCategorySortedByTitleDesc(categoryId: Long, offset: Int, limit: Int): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE category_id = :categoryId AND is_deleted = 0 ORDER BY updated_at ASC LIMIT :limit OFFSET :offset")
    fun getNotesByCategorySortedByDateAsc(categoryId: Long, offset: Int, limit: Int): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE category_id = :categoryId AND is_deleted = 0 ORDER BY updated_at DESC LIMIT :limit OFFSET :offset")
    fun getNotesByCategorySortedByDateDesc(categoryId: Long, offset: Int, limit: Int): Flow<List<NoteEntity>>

} 