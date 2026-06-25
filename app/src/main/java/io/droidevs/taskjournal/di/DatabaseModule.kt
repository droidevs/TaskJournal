package io.droidevs.taskjournal.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.droidevs.taskjournal.data.local.AppDatabase
import io.droidevs.taskjournal.data.local.dao.AttachmentDao
import io.droidevs.taskjournal.data.local.dao.CategoryDao
import io.droidevs.taskjournal.data.local.dao.ChecklistItemDao
import io.droidevs.taskjournal.data.local.dao.CommentDao
import io.droidevs.taskjournal.data.local.dao.LabelDao
import io.droidevs.taskjournal.data.local.dao.NoteDao
import io.droidevs.taskjournal.data.local.dao.ReminderDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("CREATE INDEX IF NOT EXISTS idx_categories_name ON categories(name)")

            db.execSQL("DROP INDEX IF EXISTS idx_deleted_timestamp")
            db.execSQL("DROP INDEX IF EXISTS idx_folder_deleted_timestamp")

            db.execSQL(
                """
                    CREATE TABLE IF NOT EXISTS notes_new (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        title TEXT NOT NULL,
                        content TEXT NOT NULL,
                        is_deleted INTEGER NOT NULL,
                        created_at INTEGER NOT NULL,
                        updated_at INTEGER NOT NULL,
                        is_pinned INTEGER NOT NULL,
                        category_id INTEGER,
                        FOREIGN KEY(category_id) REFERENCES categories(id) ON UPDATE NO ACTION ON DELETE SET NULL
                    )
                """.trimIndent()
            )
            db.execSQL(
                """
                    INSERT INTO notes_new (
                        id,
                        title,
                        content,
                        is_deleted,
                        created_at,
                        updated_at,
                        is_pinned,
                        category_id
                    )
                    SELECT
                        id,
                        title,
                        content,
                        is_deleted,
                        created_at,
                        updated_at,
                        is_pinned,
                        CASE
                            WHEN category_id IN (SELECT id FROM categories) THEN category_id
                            ELSE NULL
                        END
                    FROM notes
                """.trimIndent()
            )
            db.execSQL("DROP TABLE notes")
            db.execSQL("ALTER TABLE notes_new RENAME TO notes")

            db.execSQL("CREATE INDEX IF NOT EXISTS idx_notes_deleted_updated_at ON notes(is_deleted, updated_at)")
            db.execSQL("CREATE INDEX IF NOT EXISTS idx_notes_deleted_title ON notes(is_deleted, title)")
            db.execSQL("CREATE INDEX IF NOT EXISTS idx_notes_pinned_deleted_updated_at ON notes(is_pinned, is_deleted, updated_at)")
            db.execSQL("CREATE INDEX IF NOT EXISTS idx_notes_pinned_deleted_title ON notes(is_pinned, is_deleted, title)")
            db.execSQL("CREATE INDEX IF NOT EXISTS idx_notes_category_deleted_updated_at ON notes(category_id, is_deleted, updated_at)")
            db.execSQL("CREATE INDEX IF NOT EXISTS idx_notes_category_deleted_title ON notes(category_id, is_deleted, title)")
        }
    }

    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("ALTER TABLE notes ADD COLUMN is_archived INTEGER NOT NULL DEFAULT 0")
            db.execSQL("ALTER TABLE notes ADD COLUMN is_completed INTEGER NOT NULL DEFAULT 0")
            db.execSQL("ALTER TABLE notes ADD COLUMN priority TEXT NOT NULL DEFAULT 'NONE'")
            db.execSQL("ALTER TABLE notes ADD COLUMN due_at INTEGER")
            db.execSQL("ALTER TABLE notes ADD COLUMN reminder_at INTEGER")
            db.execSQL("ALTER TABLE notes ADD COLUMN completed_at INTEGER")
            db.execSQL("ALTER TABLE notes ADD COLUMN archived_at INTEGER")
            db.execSQL("ALTER TABLE notes ADD COLUMN deleted_at INTEGER")
            db.execSQL("ALTER TABLE notes ADD COLUMN recurrence_rule TEXT")
            db.execSQL("ALTER TABLE notes ADD COLUMN mood TEXT")
            db.execSQL("ALTER TABLE notes ADD COLUMN location_name TEXT")
            db.execSQL("ALTER TABLE notes ADD COLUMN location_latitude REAL")
            db.execSQL("ALTER TABLE notes ADD COLUMN location_longitude REAL")
            db.execSQL("ALTER TABLE notes ADD COLUMN weather_summary TEXT")

            db.execSQL(
                """
                    CREATE TABLE IF NOT EXISTS labels (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        name TEXT NOT NULL,
                        color INTEGER,
                        hidden INTEGER NOT NULL DEFAULT 0,
                        created_at INTEGER NOT NULL
                    )
                """.trimIndent()
            )
            db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS idx_labels_name ON labels(name)")

            db.execSQL(
                """
                    CREATE TABLE IF NOT EXISTS label_refs (
                        noteId INTEGER NOT NULL,
                        labelId INTEGER NOT NULL,
                        PRIMARY KEY(noteId, labelId),
                        FOREIGN KEY(noteId) REFERENCES notes(id) ON UPDATE NO ACTION ON DELETE CASCADE,
                        FOREIGN KEY(labelId) REFERENCES labels(id) ON UPDATE NO ACTION ON DELETE CASCADE
                    )
                """.trimIndent()
            )
            db.execSQL("CREATE INDEX IF NOT EXISTS index_label_refs_noteId ON label_refs(noteId)")
            db.execSQL("CREATE INDEX IF NOT EXISTS index_label_refs_labelId ON label_refs(labelId)")

            db.execSQL(
                """
                    CREATE TABLE IF NOT EXISTS attachments (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        note_id INTEGER NOT NULL,
                        type TEXT NOT NULL,
                        path TEXT NOT NULL,
                        file_name TEXT NOT NULL,
                        description TEXT NOT NULL,
                        mime_type TEXT,
                        size_bytes INTEGER,
                        created_at INTEGER NOT NULL,
                        FOREIGN KEY(note_id) REFERENCES notes(id) ON UPDATE NO ACTION ON DELETE CASCADE
                    )
                """.trimIndent()
            )
            db.execSQL("CREATE INDEX IF NOT EXISTS index_attachments_note_id ON attachments(note_id)")

            db.execSQL(
                """
                    CREATE TABLE IF NOT EXISTS checklist_items (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        note_id INTEGER NOT NULL,
                        text TEXT NOT NULL,
                        is_checked INTEGER NOT NULL DEFAULT 0,
                        position INTEGER NOT NULL DEFAULT 0,
                        created_at INTEGER NOT NULL,
                        updated_at INTEGER NOT NULL,
                        FOREIGN KEY(note_id) REFERENCES notes(id) ON UPDATE NO ACTION ON DELETE CASCADE
                    )
                """.trimIndent()
            )
            db.execSQL("CREATE INDEX IF NOT EXISTS index_checklist_items_note_id ON checklist_items(note_id)")

            db.execSQL(
                """
                    CREATE TABLE IF NOT EXISTS comments (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        note_id INTEGER NOT NULL,
                        text TEXT NOT NULL,
                        created_at INTEGER NOT NULL,
                        updated_at INTEGER NOT NULL,
                        FOREIGN KEY(note_id) REFERENCES notes(id) ON UPDATE NO ACTION ON DELETE CASCADE
                    )
                """.trimIndent()
            )
            db.execSQL("CREATE INDEX IF NOT EXISTS index_comments_note_id ON comments(note_id)")

            db.execSQL(
                """
                    CREATE TABLE IF NOT EXISTS reminders (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        note_id INTEGER NOT NULL,
                        trigger_at INTEGER NOT NULL,
                        title TEXT,
                        message TEXT,
                        is_enabled INTEGER NOT NULL DEFAULT 1,
                        is_done INTEGER NOT NULL DEFAULT 0,
                        recurrence_rule TEXT,
                        created_at INTEGER NOT NULL,
                        FOREIGN KEY(note_id) REFERENCES notes(id) ON UPDATE NO ACTION ON DELETE CASCADE
                    )
                """.trimIndent()
            )
            db.execSQL("CREATE INDEX IF NOT EXISTS index_reminders_note_id ON reminders(note_id)")
            db.execSQL("CREATE INDEX IF NOT EXISTS idx_reminders_trigger_at ON reminders(trigger_at)")
            db.execSQL("CREATE INDEX IF NOT EXISTS idx_reminders_note_enabled ON reminders(note_id, is_enabled)")
        }
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "task_journal_db"
        )
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(database: AppDatabase): NoteDao {
        return database.noteDao()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(database: AppDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    @Singleton
    fun provideLabelDao(database: AppDatabase): LabelDao = database.labelDao()

    @Provides
    @Singleton
    fun provideAttachmentDao(database: AppDatabase): AttachmentDao = database.attachmentDao()

    @Provides
    @Singleton
    fun provideChecklistItemDao(database: AppDatabase): ChecklistItemDao = database.checklistItemDao()

    @Provides
    @Singleton
    fun provideCommentDao(database: AppDatabase): CommentDao = database.commentDao()

    @Provides
    @Singleton
    fun provideReminderDao(database: AppDatabase): ReminderDao = database.reminderDao()
} 
