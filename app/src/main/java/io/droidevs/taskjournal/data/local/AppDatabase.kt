package io.droidevs.taskjournal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.droidevs.taskjournal.data.local.dao.AttachmentDao
import io.droidevs.taskjournal.data.local.dao.CategoryDao
import io.droidevs.taskjournal.data.local.dao.ChecklistItemDao
import io.droidevs.taskjournal.data.local.dao.CommentDao
import io.droidevs.taskjournal.data.local.dao.LabelDao
import io.droidevs.taskjournal.data.local.dao.NoteDao
import io.droidevs.taskjournal.data.local.dao.ReminderDao
import io.droidevs.taskjournal.data.local.entity.AttachmentEntity
import io.droidevs.taskjournal.data.local.entity.CategoryEntity
import io.droidevs.taskjournal.data.local.entity.ChecklistItemEntity
import io.droidevs.taskjournal.data.local.entity.CommentEntity
import io.droidevs.taskjournal.data.local.entity.LabelEntity
import io.droidevs.taskjournal.data.local.entity.LabelRef
import io.droidevs.taskjournal.data.local.entity.NoteEntity
import io.droidevs.taskjournal.data.local.entity.ReminderEntity
import io.droidevs.taskjournal.data.local.util.Converters

@Database(
    entities = [
        NoteEntity::class,
        CategoryEntity::class,
        LabelEntity::class,
        LabelRef::class,
        AttachmentEntity::class,
        ChecklistItemEntity::class,
        CommentEntity::class,
        ReminderEntity::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun categoryDao(): CategoryDao
    abstract fun labelDao(): LabelDao
    abstract fun attachmentDao(): AttachmentDao
    abstract fun checklistItemDao(): ChecklistItemDao
    abstract fun commentDao(): CommentDao
    abstract fun reminderDao(): ReminderDao
} 
