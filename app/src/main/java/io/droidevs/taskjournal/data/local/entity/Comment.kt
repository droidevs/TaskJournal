package io.droidevs.taskjournal.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@Entity(
    foreignKeys = [ForeignKey(
        entity = NoteEntity::class,
        parentColumns = arrayOf("note_id"),
        childColumns = arrayOf("note_comment_id"),
        onDelete = CASCADE
    )]
)
data class Comment(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "note_comment_id", index = true) val noteCommentId: Long,
    var text: String = "",
    @ColumnInfo(name = "create_time") var createTime: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "update_time") var updateTime: Long = System.currentTimeMillis(),
) : Parcelable