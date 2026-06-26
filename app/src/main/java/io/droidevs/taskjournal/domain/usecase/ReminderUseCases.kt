package io.droidevs.taskjournal.domain.usecase

import io.droidevs.taskjournal.domain.dispatchers.AppDispatchersProvider
import io.droidevs.taskjournal.domain.model.Reminder
import io.droidevs.taskjournal.domain.repository.ReminderRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRemindersForNoteUseCase @Inject constructor(
    private val repository: ReminderRepository,
    private val dispatchers: AppDispatchersProvider
) {
    operator fun invoke(noteId: Long): Flow<Result<List<Reminder>, DatabaseError>> =
        repository.getRemindersForNote(noteId).flowOn(dispatchers.io)
}

class GetDueRemindersUseCase @Inject constructor(
    private val repository: ReminderRepository,
    private val dispatchers: AppDispatchersProvider
) {
    operator fun invoke(now: Long = System.currentTimeMillis()): Flow<Result<List<Reminder>, DatabaseError>> =
        repository.getDueReminders(now).flowOn(dispatchers.io)
}

class InsertReminderUseCase @Inject constructor(
    private val repository: ReminderRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(reminder: Reminder): Result<Long, DatabaseError> =
        withContext(dispatchers.io) { repository.insertReminder(reminder) }
}

class UpdateReminderUseCase @Inject constructor(
    private val repository: ReminderRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(reminder: Reminder): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) { repository.updateReminder(reminder) }
}

class DeleteReminderUseCase @Inject constructor(
    private val repository: ReminderRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(reminder: Reminder): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) { repository.deleteReminder(reminder) }

    suspend operator fun invoke(id: Long): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) { repository.deleteReminderById(id) }
}

data class ReminderUseCases @Inject constructor(
    val getForNote: GetRemindersForNoteUseCase,
    val getDue: GetDueRemindersUseCase,
    val insert: InsertReminderUseCase,
    val update: UpdateReminderUseCase,
    val delete: DeleteReminderUseCase
)