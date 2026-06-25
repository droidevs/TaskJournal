package io.droidevs.taskjournal.data.repository

import io.droidevs.taskjournal.data.local.dao.NoteDao
import io.droidevs.taskjournal.data.local.entity.NoteWithCategory
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
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import io.droidevs.taskjournal.data.local.exceptions.asDatabaseResultFlowPreservingResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao
) : NoteRepository {

    private enum class NoteVisibility {
        Active,
        Pinned,
        Deleted
    }

    override fun getAllNotes(
        page: Int,
        pageSize: Int,
        order: NoteOrder
    ): Flow<Result<List<Note>, DatabaseError>> {
        return dao.observeNotes(
            buildNotesQuery(
                visibility = NoteVisibility.Active,
                page = page,
                pageSize = pageSize,
                order = order
            )
        ).toResultFlow()
    }

    override fun getNoteById(id: Long): Flow<Result<Note?, DatabaseError>> {
        return dao.getNoteById(id)
            .map { entity ->
                Result.Success(entity?.toDomain())
            }
            .asDatabaseResultFlowPreservingResult()
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
        return dao.observeNotes(
            buildNotesQuery(
                visibility = NoteVisibility.Pinned,
                page = page,
                pageSize = pageSize,
                order = order
            )
        ).toResultFlow()
    }

    override fun getDeletedNotes(
        page: Int,
        pageSize: Int,
        order: NoteOrder
    ): Flow<Result<List<Note>, DatabaseError>> {
        return dao.observeNotes(
            buildNotesQuery(
                visibility = NoteVisibility.Deleted,
                page = page,
                pageSize = pageSize,
                order = order
            )
        ).toResultFlow()
    }

    override fun searchNotes(
        query: String,
        page: Int,
        pageSize: Int,
        order: NoteOrder
    ): Flow<Result<List<Note>, DatabaseError>> {
        return dao.observeNotes(
            buildNotesQuery(
                visibility = NoteVisibility.Active,
                page = page,
                pageSize = pageSize,
                order = order,
                searchQuery = query
            )
        ).toResultFlow()
    }

    override fun getNotesByCategory(
        categoryId: Long,
        page: Int,
        pageSize: Int,
        order: NoteOrder
    ): Flow<Result<List<Note>, DatabaseError>> {
        return dao.observeNotes(
            buildNotesQuery(
                visibility = NoteVisibility.Active,
                page = page,
                pageSize = pageSize,
                order = order,
                categoryId = categoryId
            )
        ).toResultFlow()
    }

    private fun buildNotesQuery(
        visibility: NoteVisibility,
        page: Int,
        pageSize: Int,
        order: NoteOrder,
        searchQuery: String? = null,
        categoryId: Long? = null
    ): SupportSQLiteQuery {
        val filters = mutableListOf<String>()
        val args = mutableListOf<Any>()

        when (visibility) {
            NoteVisibility.Active -> filters += "is_deleted = 0"
            NoteVisibility.Pinned -> {
                filters += "is_pinned = 1"
                filters += "is_deleted = 0"
            }
            NoteVisibility.Deleted -> filters += "is_deleted = 1"
        }

        categoryId?.let {
            filters += "category_id = ?"
            args += it
        }

        searchQuery
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
            ?.let {
                filters += "(title LIKE ? OR content LIKE ?)"
                val pattern = "%$it%"
                args += pattern
                args += pattern
            }

        args += safePageSize(pageSize)
        args += safeOffset(page, pageSize)

        return SimpleSQLiteQuery(
            """
                SELECT *
                FROM notes
                WHERE ${filters.joinToString(" AND ")}
                ORDER BY ${order.toSqlOrder()}, id DESC
                LIMIT ? OFFSET ?
            """.trimIndent(),
            args.toTypedArray()
        )
    }

    private fun Flow<List<NoteWithCategory>>.toResultFlow(): Flow<Result<List<Note>, DatabaseError>> {
        return map { entities ->
            Result.Success(entities.map { it.toDomain() })
        }.asDatabaseResultFlowPreservingResult()
    }

    private fun safePageSize(pageSize: Int): Int = pageSize.coerceAtLeast(1)

    private fun safeOffset(page: Int, pageSize: Int): Int {
        return (page.coerceAtLeast(1) - 1) * safePageSize(pageSize)
    }

    private fun NoteOrder.toSqlOrder(): String {
        val column = when (this) {
            is NoteOrder.Title -> "title"
            is NoteOrder.Date -> "updated_at"
        }
        return "$column ${orderType.toSqlDirection()}"
    }

    private fun OrderType.toSqlDirection(): String {
        return when (this) {
            is OrderType.Ascending -> "ASC"
            is OrderType.Descending -> "DESC"
        }
    }
}
