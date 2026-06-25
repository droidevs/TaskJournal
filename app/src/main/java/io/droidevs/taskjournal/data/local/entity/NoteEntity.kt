package io.droidevs.taskjournal.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import io.droidevs.taskjournal.domain.model.JournalMood
import io.droidevs.taskjournal.domain.model.NotePriority

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
    val categoryId: Long? = null,
    @ColumnInfo(name = "is_archived")
    val isArchived: Boolean = false,
    @ColumnInfo(name = "is_completed")
    val isCompleted: Boolean = false,
    @ColumnInfo(name = "priority")
    val priority: NotePriority = NotePriority.NONE,
    @ColumnInfo(name = "due_at")
    val dueAt: Long? = null,
    @ColumnInfo(name = "reminder_at")
    val reminderAt: Long? = null,
    @ColumnInfo(name = "completed_at")
    val completedAt: Long? = null,
    @ColumnInfo(name = "archived_at")
    val archivedAt: Long? = null,
    @ColumnInfo(name = "deleted_at")
    val deletedAt: Long? = null,
    @ColumnInfo(name = "recurrence_rule")
    val recurrenceRule: String? = null,
    @ColumnInfo(name = "mood")
    val mood: JournalMood? = null,
    @ColumnInfo(name = "location_name")
    val locationName: String? = null,
    @ColumnInfo(name = "location_latitude")
    val locationLatitude: Double? = null,
    @ColumnInfo(name = "location_longitude")
    val locationLongitude: Double? = null,
    @ColumnInfo(name = "weather_summary")
    val weatherSummary: String? = null
)
