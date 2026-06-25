package io.droidevs.taskjournal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.droidevs.taskjournal.data.local.dao.CategoryDao
import io.droidevs.taskjournal.data.local.dao.NoteDao
import io.droidevs.taskjournal.data.local.entity.CategoryEntity
import io.droidevs.taskjournal.data.local.entity.NoteEntity
import io.droidevs.taskjournal.data.local.util.Converters

@Database(
    entities = [NoteEntity::class, CategoryEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun categoryDao(): CategoryDao
} 
