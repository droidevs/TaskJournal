package io.droidevs.taskjournal.data.repository

import io.droidevs.taskjournal.data.local.dao.ReminderDao
import io.droidevs.taskjournal.data.local.exceptions.asDatabaseResultFlowPreservingResult
import io.droidevs.taskjournal.data.local.exceptions.runCatchingDatabaseResult
import io.droidevs.taskjournal.data.mappers.toDomain
import io.droidevs.taskjournal.data.mappers.toEntity
import io.droidevs.taskjournal.domain.model.Reminder
import io.droidevs.taskjournal.domain.repository.ReminderRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val dao: ReminderDao
) : ReminderRepository {
    override fun getRemindersForNote(noteId: Long): Flow<Result<List<Reminder>, DatabaseError>> {
        return dao.observeByNoteId(noteId)
            .map { reminders -> Result.Success(reminders.map { it.toDomain() }) }
            .asDatabaseResultFlowPreservingResult()
    }

    override fun getDueReminders(now: Long): Flow<Result<List<Reminder>, DatabaseError>> {
        return dao.observeDueReminders(now)
            .map { reminders -> Result.Success(reminders.map { it.toDomain() }) }
            .asDatabaseResultFlowPreservingResult()
    }

    override suspend fun insertReminder(reminder: Reminder): Result<Long, DatabaseError> {
        return runCatchingDatabaseResult { dao.insert(reminder.toEntity()) }
    }

    override suspend fun updateReminder(reminder: Reminder): Result<Unit, DatabaseError> {
        return runCatchingDatabaseResult { dao.update(reminder.toEntity()) }
    }

    override suspend fun deleteReminder(reminder: Reminder): Result<Unit, DatabaseError> {
        return runCatchingDatabaseResult { dao.delete(reminder.toEntity()) }
    }

    override suspend fun deleteReminderById(id: Long): Result<Unit, DatabaseError> {
        return runCatchingDatabaseResult { dao.deleteById(id) }
    }
}

