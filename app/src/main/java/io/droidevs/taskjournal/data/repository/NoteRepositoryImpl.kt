package io.droidevs.taskjournal.data.repository

import io.droidevs.taskjournal.data.local.dao.NoteDao
import io.droidevs.taskjournal.data.local.entity.NoteEntity
import io.droidevs.taskjournal.data.local.exceptions.asDatabaseResultFlow
import io.droidevs.taskjournal.data.local.exceptions.runCatchingDatabaseResult
import io.droidevs.taskjournal.data.mappers.toDomain
import io.droidevs.taskjournal.data.mappers.toEntity
import io.droidevs.taskjournal.domain.model.Note
import io.droidevs.taskjournal.domain.model.NoteOrder
import io.droidevs.taskjournal.domain.model.OrderType
import io.droidevs.taskjournal.domain.repository.NoteRepository
import io.droidevs.taskjournal.domain.result.Result
import io.droidevs.taskjournal.domain.result.errors.DatabaseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao
) : NoteRepository {

    override fun getAllNotes(
        page: Int,
        pageSize: Int,
        order: NoteOrder
    ): Flow<Result<List<Note>, DatabaseError>> {
        var offset = (page - 1) * pageSize
        var limit = pageSize
        return when (order) {
            is NoteOrder.Title -> {
                when (order.orderType) {
                    is OrderType.Ascending -> dao.getAllNotesSortedByTitleAsc(offset = offset, limit = limit)
                    is OrderType.Descending -> dao.getAllNotesSortedByTitleDesc(offset = offset, limit = limit)
                }
            }
            is NoteOrder.Date -> {
                when (order.orderType) {
                    is OrderType.Ascending -> dao.getAllNotesSortedByDateAsc(offset = offset, limit = limit)
                    is OrderType.Descending -> dao.getAllNotesSortedByDateDesc(offset = offset, limit = limit)
                }
            }
        }.map { entities ->
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

    override fun getPinnedNotes(
        page: Int,
        pageSize: Int,
        order: NoteOrder
    ): Flow<Result<List<Note>, DatabaseError>> {
        var offset = (page - 1) * pageSize
        var limit = pageSize
        return when (order) {
            is NoteOrder.Title -> {
                when (order.orderType) {
                    is OrderType.Ascending -> dao.getPinnedNotesSortedByTitleAsc(
                        offset = offset,
                        limit = limit
                    )

                    is OrderType.Descending -> dao.getPinnedNotesSortedByTitleDesc(
                        offset = offset,
                        limit = limit
                    )
                }
            }
            is NoteOrder.Date -> {
                when (order.orderType) {
                    is OrderType.Ascending -> dao.getPinnedNotesSortedByDateAsc(
                        offset = offset,
                        limit = limit
                    )

                    is OrderType.Descending -> dao.getPinnedNotesSortedByDateDesc(
                        offset = offset,
                        limit = limit
                    )
                }
            }
        }
            .map { entities ->
                Result.Success(entities.map { it.toDomain() })
            }
            .asDatabaseResultFlow()
    }

    override fun getDeletedNotes(
        page: Int,
        pageSize: Int,
        order: NoteOrder
    ): Flow<Result<List<Note>, DatabaseError>> {
        var offset = (page - 1) * pageSize
        var limit = pageSize
        return when (order) {
            is NoteOrder.Title -> {
                when (order.orderType) {
                    is OrderType.Ascending -> dao.getDeletedNotesSortedByTitleAsc(offset = offset, limit = limit)
                    is OrderType.Descending -> dao.getDeletedNotesSortedByTitleDesc(offset = offset, limit = limit)
                }
            }
            is NoteOrder.Date -> {
                when (order.orderType) {
                    is OrderType.Ascending -> dao.getDeletedNotesSortedByDateAsc(offset = offset, limit = limit)
                    is OrderType.Descending -> dao.getDeletedNotesSortedByDateDesc(offset = offset, limit = limit)
                }
            }
        }
            .map { entities ->
                Result.Success(entities.map { it.toDomain() })
            }
            .asDatabaseResultFlow()
    }

    override fun searchNotes(
        query: String,
        page: Int,
        pageSize: Int,
        order: NoteOrder
    ): Flow<Result<List<Note>, DatabaseError>> {
        var offset = (page - 1) * pageSize
        var limit = pageSize
        val noteOrder = order
        return when (noteOrder) {
            is NoteOrder.Title -> {
                when (noteOrder.orderType) {
                    is OrderType.Ascending -> dao.searchNotesSortedByTitleAsc(query, offset, limit)
                    is OrderType.Descending -> dao.searchNotesSortedByTitleDesc(query, offset, limit)
                }
            }
            is NoteOrder.Date -> {
                when (noteOrder.orderType) {
                    is OrderType.Ascending -> dao.searchNotesSortedByDateAsc(query, offset, limit)
                    is OrderType.Descending -> dao.searchNotesSortedByDateDesc(query, offset, limit)
                }
            }
        }
            .map { entities ->
                Result.Success(entities.map { it.toDomain()})
            }
            .asDatabaseResultFlow()
    }

    override fun getNotesByCategory(
        categoryId: Long,
        page: Int,
        pageSize: Int,
        order: NoteOrder
    ): Flow<Result<List<Note>, DatabaseError>> {
        var offset = (page - 1) * pageSize
        var limit = pageSize
        return when (order) {
            is NoteOrder.Title -> {
                when (order.orderType) {
                    is OrderType.Ascending -> dao.getNotesByCategorySortedByTitleAsc(
                        categoryId = categoryId,
                        offset = offset,
                        limit = limit
                    )

                    is OrderType.Descending -> dao.getNotesByCategorySortedByTitleDesc(
                        categoryId = categoryId,
                        offset = offset,
                        limit = limit
                    )
                }
            }
            is NoteOrder.Date -> {
                when (order.orderType) {
                    is OrderType.Ascending -> dao.getNotesByCategorySortedByDateAsc(
                        categoryId = categoryId,
                        offset = offset,
                        limit = limit
                    )

                    is OrderType.Descending -> dao.getNotesByCategorySortedByDateDesc(
                        categoryId = categoryId,
                        offset = offset,
                        limit = limit
                    )
                }
            }
        }
            .map { entities ->
                Result.Success(entities.map { it.toDomain() })
            }
            .asDatabaseResultFlow()
    }
} 