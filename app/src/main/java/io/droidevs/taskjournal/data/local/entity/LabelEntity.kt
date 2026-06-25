package io.droidevs.taskjournal.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "labels")
@Parcelize
data class LabelEntity(
    /**
     * Label ID in the database.
     * ID is transient during serialization since labels are mapped by ID in JSON,
     * so repeating this field would be superfluous.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @Transient
    val id: Long = NO_ID,

    /**
     * Label name, cannot be blank.
     */
    @ColumnInfo(name = "name", index = true)
    val name: String,

    /**
     * Whether the notes with this label will be hidden from active/archived destinations.
     * These notes will only be visible in the label destinations.
     */
    @ColumnInfo(name = "hidden")
    val hidden: Boolean = false,
) : Parcelable {

    companion object {
        const val NO_ID = 0L
    }
}
