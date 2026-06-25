package io.droidevs.taskjournal.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.droidevs.taskjournal.data.repository.AttachmentRepositoryImpl
import io.droidevs.taskjournal.data.repository.NoteRepositoryImpl
import io.droidevs.taskjournal.data.repository.CategoryRepositoryImpl
import io.droidevs.taskjournal.data.repository.ChecklistItemRepositoryImpl
import io.droidevs.taskjournal.data.repository.CommentRepositoryImpl
import io.droidevs.taskjournal.data.repository.LabelRepositoryImpl
import io.droidevs.taskjournal.data.repository.ReminderRepositoryImpl
import io.droidevs.taskjournal.domain.repository.AttachmentRepository
import io.droidevs.taskjournal.domain.repository.NoteRepository
import io.droidevs.taskjournal.domain.repository.CategoryRepository
import io.droidevs.taskjournal.domain.repository.ChecklistItemRepository
import io.droidevs.taskjournal.domain.repository.CommentRepository
import io.droidevs.taskjournal.domain.repository.LabelRepository
import io.droidevs.taskjournal.domain.repository.ReminderRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideNoteRepository(
        noteRepositoryImpl: NoteRepositoryImpl
    ): NoteRepository = noteRepositoryImpl

    @Provides
    @Singleton
    fun provideCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository = categoryRepositoryImpl

    @Provides
    @Singleton
    fun provideLabelRepository(
        labelRepositoryImpl: LabelRepositoryImpl
    ): LabelRepository = labelRepositoryImpl

    @Provides
    @Singleton
    fun provideAttachmentRepository(
        attachmentRepositoryImpl: AttachmentRepositoryImpl
    ): AttachmentRepository = attachmentRepositoryImpl

    @Provides
    @Singleton
    fun provideChecklistItemRepository(
        checklistItemRepositoryImpl: ChecklistItemRepositoryImpl
    ): ChecklistItemRepository = checklistItemRepositoryImpl

    @Provides
    @Singleton
    fun provideCommentRepository(
        commentRepositoryImpl: CommentRepositoryImpl
    ): CommentRepository = commentRepositoryImpl

    @Provides
    @Singleton
    fun provideReminderRepository(
        reminderRepositoryImpl: ReminderRepositoryImpl
    ): ReminderRepository = reminderRepositoryImpl
} 
