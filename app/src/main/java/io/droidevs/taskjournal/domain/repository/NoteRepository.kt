package io.droidevs.taskjournal.domain.repository

import io.droidevs.taskjournal.domain.model.Note
import io.droidevs.taskjournal.domain.model.NoteOrder
import io.droidevs.taskjournal.domain.model.OrderType
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(
        page: Int = 1,
        pageSize: Int = 20,
        order: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<Result<List<Note>, DatabaseError>>
    fun getNoteById(id: Long): Flow<Result<Note?, DatabaseError>>
    suspend fun insertNote(note: Note): Result<Long, DatabaseError>
    suspend fun updateNote(note: Note): Result<Unit,DatabaseError>
    suspend fun deleteNote(note: Note): Result<Unit,DatabaseError>
    suspend fun deleteNoteById(id: Long): Result<Unit, DatabaseError>
    fun getPinnedNotes(
        page: Int = 1,
        pageSize: Int = 20,
        order: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<Result<List<Note>, DatabaseError>>

    fun getDeletedNotes(
        page: Int = 1,
        pageSize: Int = 20,
        order: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<Result<List<Note>, DatabaseError>>
    fun searchNotes(
        query: String,
        page: Int = 1,
        pageSize: Int = 20,
        order: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<Result<List<Note>, DatabaseError>>


    fun getNotesByCategory(
        categoryId: Long,
        page: Int = 1,
        pageSize: Int = 20,
        order: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<Result<List<Note>, DatabaseError>>

} 
