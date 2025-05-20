package io.droidevs.taskjournal.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.droidevs.taskjournal.domain.model.Category

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val color: Int
)