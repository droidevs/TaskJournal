package io.droidevs.taskjournal.domain.repository

import io.droidevs.taskjournal.domain.model.Reminder
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    fun getRemindersForNote(noteId: Long): Flow<Result<List<Reminder>, DatabaseError>>
    fun getDueReminders(now: Long = System.currentTimeMillis()): Flow<Result<List<Reminder>, DatabaseError>>
    suspend fun insertReminder(reminder: Reminder): Result<Long, DatabaseError>
    suspend fun updateReminder(reminder: Reminder): Result<Unit, DatabaseError>
    suspend fun deleteReminder(reminder: Reminder): Result<Unit, DatabaseError>
    suspend fun deleteReminderById(id: Long): Result<Unit, DatabaseError>
}

