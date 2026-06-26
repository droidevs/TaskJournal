package io.droidevs.taskjournal.domain.usecase

import io.droidevs.taskjournal.domain.dispatchers.AppDispatchersProvider
import io.droidevs.taskjournal.domain.model.Note
import io.droidevs.taskjournal.domain.model.NoteOrder
import io.droidevs.taskjournal.domain.repository.NoteRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val dispatchersProvider: AppDispatchersProvider
) {
    suspend operator fun invoke(note: Note): Result<Unit, DatabaseError> = withContext(dispatchersProvider.io) {
        repository.deleteNote(note)
    }
}

class GetAllNotesUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val dispatchers: AppDispatchersProvider
) {
    operator fun invoke(
        page: Int,
        pageSize: Int,
        order: NoteOrder
    ): Flow<Result<List<Note>, DatabaseError>> =
        repository.getAllNotes(page = page, pageSize = pageSize,order = order)
            .flowOn(dispatchers.io)

}
class GetDeletedNotesUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val dispatchers: AppDispatchersProvider
) {
    operator fun invoke(
        page: Int,
        pageSize: Int,
        order: NoteOrder
    ): Flow<Result<List<Note>, DatabaseError>> =
        repository.getDeletedNotes(page = page, pageSize = pageSize,order = order)
            .flowOn(dispatchers.io)

}

class GetPinnedNotesUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val dispatchers: AppDispatchersProvider
) {
    operator fun invoke(
        page: Int,
        pageSize: Int,
        order: NoteOrder
    ): Flow<Result<List<Note>, DatabaseError>> =
        repository.getPinnedNotes(page = page, pageSize = pageSize,order = order)
            .flowOn(dispatchers.io)

}

class UpdateNoteUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(note: Note): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) {
            repository.updateNote(note)
        }
}

class SearchNotesUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val dispatchers: AppDispatchersProvider
) {
    operator fun invoke(
        query : String,
        page: Int,
        pageSize: Int,
        order: NoteOrder
    ): Flow<Result<List<Note>, DatabaseError>> =
        repository.searchNotes(query = query, page = page, pageSize = pageSize,order = order)
            .flowOn(dispatchers.io)

}

class InsertNoteUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(note: Note): Result<Long, DatabaseError> =
        withContext(dispatchers.io) {
            repository.insertNote(note)
        }
}

class GetNoteByIdUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val dispatchers: AppDispatchersProvider
) {
    operator fun invoke(id: Long): Flow<Result<Note?, DatabaseError>> =
        repository.getNoteById(id)
            .flowOn(dispatchers.io)
}

/**
 * Notes belonging to a particular category, paged and ordered.
 */
class GetNotesByCategoryUseCase(
    private val noteRepository: NoteRepository,
    private val dispatchers: AppDispatchersProvider
) {

    operator fun invoke(
        category: Long,
        page: Int,
        pageSize: Int,
        order : NoteOrder
    ) = noteRepository.getNotesByCategory(
        categoryId = category,
        page = page,
        pageSize = pageSize,
        order = order
    ).flowOn(dispatchers.io)

}

/**
 * Flips [Note.isPinned]. Soft-toggle convenience over [UpdateNoteUseCase] so
 * ViewModels don't need to hand-roll the `.copy(isPinned = !isPinned)` everywhere.
 */
class TogglePinNoteUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(note: Note): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) {
            repository.updateNote(note.copy(isPinned = !note.isPinned))
        }
}

/**
 * Marks a note completed/incomplete, stamping [Note.completedAt] accordingly.
 */
class ToggleCompletedNoteUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(note: Note): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) {
            val nowCompleted = !note.isCompleted
            repository.updateNote(
                note.copy(
                    isCompleted = nowCompleted,
                    completedAt = if (nowCompleted) System.currentTimeMillis() else null
                )
            )
        }
}

/**
 * Archives or unarchives a note, stamping [Note.archivedAt] accordingly.
 */
class ArchiveNoteUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(note: Note, archived: Boolean = true): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) {
            repository.updateNote(
                note.copy(
                    isArchived = archived,
                    archivedAt = if (archived) System.currentTimeMillis() else null
                )
            )
        }
}

/**
 * Soft-deletes (moves to trash) a note, stamping [Note.deletedAt].
 * Use [PermanentlyDeleteNoteUseCase] to remove it for good.
 */
class TrashNoteUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(note: Note): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) {
            repository.updateNote(note.copy(isDeleted = true, deletedAt = System.currentTimeMillis()))
        }
}

/**
 * Restores a soft-deleted note back to the active list.
 */
class RestoreNoteUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(note: Note): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) {
            repository.updateNote(note.copy(isDeleted = false, deletedAt = null))
        }
}

/**
 * Permanently removes a note (e.g. emptying the trash), as opposed to
 * [TrashNoteUseCase] which only soft-deletes.
 */
class PermanentlyDeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository,
    private val dispatchers: AppDispatchersProvider
) {
    suspend operator fun invoke(note: Note): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) { repository.deleteNote(note) }

    suspend operator fun invoke(id: Long): Result<Unit, DatabaseError> =
        withContext(dispatchers.io) { repository.deleteNoteById(id) }
}

class NotesUseCases(
    val add : InsertNoteUseCase,
    val delete : DeleteNoteUseCase,
    val update : UpdateNoteUseCase,
    val get : GetAllNotesUseCase,
    val search : SearchNotesUseCase,
    val getDeleted : GetDeletedNotesUseCase,
    val getPinned: GetPinnedNotesUseCase,
    val getById : GetNoteByIdUseCase,
    val getByCategory : GetNotesByCategoryUseCase,
    val togglePin : TogglePinNoteUseCase,
    val toggleCompleted : ToggleCompletedNoteUseCase,
    val archive : ArchiveNoteUseCase,
    val trash : TrashNoteUseCase,
    val restore : RestoreNoteUseCase,
    val permanentlyDelete : PermanentlyDeleteNoteUseCase
)