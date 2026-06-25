package io.droidevs.taskjournal.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "comments",
    foreignKeys = [ForeignKey(
        entity = NoteEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("note_id"),
        onDelete = CASCADE
    )]
)
data class CommentEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "note_id", index = true) val noteId: Long,
    var text: String = "",
    @ColumnInfo(name = "created_at") var createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at") var updatedAt: Long = System.currentTimeMillis(),
)
