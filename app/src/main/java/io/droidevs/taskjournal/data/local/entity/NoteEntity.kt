package io.droidevs.taskjournal.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "notes",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["is_deleted", "updated_at"], name = "idx_notes_deleted_updated_at"),
        Index(value = ["is_deleted", "title"], name = "idx_notes_deleted_title"),
        Index(value = ["is_pinned", "is_deleted", "updated_at"], name = "idx_notes_pinned_deleted_updated_at"),
        Index(value = ["is_pinned", "is_deleted", "title"], name = "idx_notes_pinned_deleted_title"),
        Index(value = ["category_id", "is_deleted", "updated_at"], name = "idx_notes_category_deleted_updated_at"),
        Index(value = ["category_id", "is_deleted", "title"], name = "idx_notes_category_deleted_title")
    ]
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "is_deleted")
    val isDeleted: Boolean = false,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long,
    @ColumnInfo(name = "is_pinned")
    val isPinned: Boolean = false,
    @ColumnInfo(name = "category_id")
    val categoryId: Long? = null
)
