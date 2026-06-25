package io.droidevs.taskjournal.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "labels",
    indices = [
        Index(value = ["name"], unique = true, name = "idx_labels_name")
    ]
)
data class LabelEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = NO_ID,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "color")
    val color: Int? = null,
    @ColumnInfo(name = "hidden")
    val hidden: Boolean = false,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
) {

    companion object {
        const val NO_ID = 0L
    }
}
