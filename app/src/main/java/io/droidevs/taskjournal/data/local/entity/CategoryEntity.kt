package io.droidevs.taskjournal.data.local.entity

import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.droidevs.taskjournal.domain.model.Category
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val color: Int
){
    companion object {
        val folderColors = listOf(
            Red,
            Yellow,
            Green,
            Cyan,
            Blue
        )
    }
}