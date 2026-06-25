package io.droidevs.taskjournal.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.droidevs.taskjournal.data.local.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders WHERE note_id = :noteId ORDER BY trigger_at ASC")
    fun observeByNoteId(noteId: Long): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminders WHERE is_enabled = 1 AND is_done = 0 AND trigger_at <= :now ORDER BY trigger_at ASC")
    fun observeDueReminders(now: Long): Flow<List<ReminderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: ReminderEntity): Long

    @Update
    suspend fun update(reminder: ReminderEntity)

    @Delete
    suspend fun delete(reminder: ReminderEntity)

    @Query("DELETE FROM reminders WHERE id = :id")
    suspend fun deleteById(id: Long)
}

