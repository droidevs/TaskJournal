package io.droidevs.taskjournal.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.droidevs.taskjournal.data.local.entity.LabelEntity
import io.droidevs.taskjournal.data.local.entity.LabelRef
import kotlinx.coroutines.flow.Flow

@Dao
interface LabelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(label: LabelEntity): Long

    @Update
    suspend fun update(label: LabelEntity)

    @Delete
    suspend fun delete(label: LabelEntity)

    @Delete
    suspend fun deleteAll(labels: List<LabelEntity>)

    /**
     * Used for clearing all data.
     */
    @Query("DELETE FROM labels")
    suspend fun clear()

    /**
     * Get all labels in database
     * Used for exporting data.
     */
    @Query("SELECT * FROM labels")
    suspend fun getAll(): List<LabelEntity>

    /**
     * Get all labels in database, sorted by most used first, then by name.
     * Used for viewing labels.
     * Left join so that labels with no references are returned.
     */
    @Query("""SELECT labels.* FROM labels LEFT JOIN label_refs ON labelId == id GROUP BY id
                    ORDER BY CASE WHEN labelId IS NULL THEN 0 ELSE COUNT(*) END DESC, name ASC""")
    fun getAllByUsage(): Flow<List<LabelEntity>>

    @Query("SELECT * FROM labels ORDER BY name ASC LIMIT :limit OFFSET :offset")
    fun getPaged(offset: Int, limit: Int): Flow<List<LabelEntity>>

    @Query("SELECT * FROM labels WHERE name LIKE '%' || :query || '%' ORDER BY name ASC LIMIT :limit OFFSET :offset")
    fun search(query: String, offset: Int, limit: Int): Flow<List<LabelEntity>>

    /**
     * Get a label by its ID. Returns `null` if label doesn't exist.
     */
    @Query("SELECT * FROM labels WHERE id == :id")
    fun observeById(id: Long): Flow<LabelEntity?>

    @Query("SELECT * FROM labels WHERE id == :id")
    suspend fun getById(id: Long): LabelEntity?

    /**
     * Get a label by its name, or `null` if none exists. Name must match exactly.
     * Used to ensure name uniqueness and for searching by label.
     */
    @Query("SELECT * FROM labels WHERE name == :name")
    suspend fun getLabelByName(name: String): LabelEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRefs(refs: List<LabelRef>)

    @Delete
    suspend fun deleteRefs(refs: List<LabelRef>)

    /**
     * Get all label references for a note by ID.
     * Used to remove old label references when changing labels on a note.
     */
    @Query("SELECT labelId FROM label_refs WHERE noteId == :noteId")
    suspend fun getLabelIdsForNote(noteId: Long): List<Long>

    /**
     * Returns the number of references to a label ID.
     * Used when deleting a label to show confirmation or not.
     */
    @Query("SELECT COUNT(*) FROM label_refs WHERE labelId == :labelId")
    suspend fun countRefs(labelId: Long): Long

    @Query("DELETE FROM label_refs WHERE noteId == :noteId")
    suspend fun clearRefsForNote(noteId: Long)
}
