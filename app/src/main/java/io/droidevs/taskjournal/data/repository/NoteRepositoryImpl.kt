package io.droidevs.taskjournal.data.repository

import io.droidevs.taskjournal.data.local.dao.NoteDao
import io.droidevs.taskjournal.data.local.entity.NoteEntity
import io.droidevs.taskjournal.data.local.exceptions.asDatabaseResultFlow
import io.droidevs.taskjournal.data.local.exceptions.runCatchingDatabaseResult
import io.droidevs.taskjournal.data.mappers.toDomain
import io.droidevs.taskjournal.data.mappers.toEntity
import io.droidevs.taskjournal.domain.model.Note
import io.droidevs.taskjournal.domain.repository.NoteRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao
) : NoteRepository {

    override fun getAllNotes(): Flow<Result<List<Note>, DatabaseError>> {
        return dao.getAllNotes()
            .map { entities ->
                Result.Success(entities.map { it.toDomain() })
            }
            .asDatabaseResultFlow()
    }

    override fun getNoteById(id: Long): Flow<Result<Note?, DatabaseError>> {
        return dao.getNoteById(id)
            .map { entity ->
                Result.Success(entity?.toDomain())
            }
            .asDatabaseResultFlow()
    }

    override suspend fun insertNote(note: Note): Result<Long, DatabaseError> {
        return runCatchingDatabaseResult {
            dao.insertNote(note.toEntity())
        }
    }

    override suspend fun updateNote(note: Note): Result<Unit, DatabaseError> {
        return runCatchingDatabaseResult {
            dao.updateNote(note.toEntity())
        }
    }

    override suspend fun deleteNote(note: Note): Result<Unit, DatabaseError> {
        return runCatchingDatabaseResult {
            dao.deleteNote(note.toEntity())
        }
    }

    override suspend fun deleteNoteById(id: Long): Result<Unit, DatabaseError> {
        return runCatchingDatabaseResult {
            dao.deleteNoteById(id)
        }
    }

    override fun getPinnedNotes(): Flow<Result<List<Note>, DatabaseError>> {
        return dao.getPinnedNotes()
            .map { entities ->
                Result.Success(entities.map { it.toDomain() })
            }
            .asDatabaseResultFlow()
    }

    override fun searchNotes(query: String): Flow<Result<List<Note>, DatabaseError>> {
        return dao.searchNotes(query)
            .map { entities ->
                Result.Success(entities.map { it.toDomain()})
            }
            .asDatabaseResultFlow()
    }
} 