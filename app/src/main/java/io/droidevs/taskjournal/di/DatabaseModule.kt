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
import io.droidevs.taskjournal.data.local.dao.CategoryDao
import io.droidevs.taskjournal.data.local.dao.NoteDao
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
            .addMigrations(MIGRATION_1_2)
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
} 
