package io.droidevs.taskjournal.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import io.droidevs.taskjournal.domain.model.Note
import java.util.Date

@Entity(
    tableName = "notes",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        // getAllNotes getAllDeletedNotes
        Index(value = ["isDeleted", "timestamp"], name = "idx_deleted_timestamp"),
        // getNotesByFolderId
        Index(value = ["categoryId", "isDeleted", "timestamp"], name = "idx_folder_deleted_timestamp")
    ]
)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val isMarkdown: Boolean = true,
    val isDeleted: Boolean = false,
    val createdAt: Date,
    val updatedAt: Date,
    val isPinned: Boolean = false,
    val categoryId: Long? = null
)