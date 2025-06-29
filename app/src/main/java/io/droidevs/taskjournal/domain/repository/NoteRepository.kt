package io.droidevs.taskjournal.domain.repository

import io.droidevs.taskjournal.domain.model.Category
import io.droidevs.taskjournal.domain.model.Note
import io.droidevs.taskjournal.domain.pager.Paginator
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DataError
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<Result<List<Note>, DatabaseError>>
    fun getNoteById(id: Long): Flow<Result<Note?, DatabaseError>>
    suspend fun insertNote(note: Note): Result<Long, DatabaseError>
    suspend fun updateNote(note: Note): Result<Unit,DatabaseError>
    suspend fun deleteNote(note: Note): Result<Unit,DatabaseError>
    suspend fun deleteNoteById(id: Long): Result<Unit, DatabaseError>
    fun getPinnedNotes(): Flow<Result<List<Note>, DatabaseError>>
    fun searchNotes(query: String): Flow<Result<List<Note>, DatabaseError>>

} 