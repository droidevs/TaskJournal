package io.droidevs.taskjournal.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.droidevs.taskjournal.domain.dispatchers.AppDispatchersProvider
import io.droidevs.taskjournal.domain.dispatchers.DefaultDispatcherProvider
import io.droidevs.taskjournal.domain.repository.NoteRepository
import io.droidevs.taskjournal.domain.repository.CategoryRepository
import io.droidevs.taskjournal.domain.usecase.note.GetAllNotesUseCase
import io.droidevs.taskjournal.domain.usecase.note.GetNoteByIdUseCase
import io.droidevs.taskjournal.domain.usecase.note.InsertNoteUseCase
import io.droidevs.taskjournal.domain.usecase.note.UpdateNoteUseCase
import io.droidevs.taskjournal.domain.usecase.note.DeleteNoteUseCase
import io.droidevs.taskjournal.domain.usecase.category.GetAllCategoriesUseCase
import io.droidevs.taskjournal.domain.usecase.category.GetCategoryByIdUseCase
import io.droidevs.taskjournal.domain.usecase.category.InsertCategoryUseCase
import io.droidevs.taskjournal.domain.usecase.category.UpdateCategoryUseCase
import io.droidevs.taskjournal.domain.usecase.category.DeleteCategoryUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideDispatchersProvider(): AppDispatchersProvider = DefaultDispatcherProvider()

    @Provides
    @Singleton
    fun provideGetAllNotesUseCase(
        noteRepository: NoteRepository,
        dispatchers: AppDispatchersProvider
    ) = GetAllNotesUseCase(noteRepository, dispatchers)

    @Provides
    @Singleton
    fun provideGetNoteByIdUseCase(
        noteRepository: NoteRepository,
        dispatchers: AppDispatchersProvider
    ) = GetNoteByIdUseCase(noteRepository, dispatchers)

    @Provides
    @Singleton
    fun provideInsertNoteUseCase(
        noteRepository: NoteRepository,
        dispatchers: AppDispatchersProvider
    ) = InsertNoteUseCase(noteRepository, dispatchers)

    @Provides
    @Singleton
    fun provideUpdateNoteUseCase(
        noteRepository: NoteRepository,
        dispatchers: AppDispatchersProvider
    ) = UpdateNoteUseCase(noteRepository, dispatchers)

    @Provides
    @Singleton
    fun provideDeleteNoteUseCase(
        noteRepository: NoteRepository,
        dispatchers: AppDispatchersProvider
    ) = DeleteNoteUseCase(noteRepository, dispatchers)

    @Provides
    @Singleton
    fun provideGetAllCategoriesUseCase(
        categoryRepository: CategoryRepository,
        dispatchers: AppDispatchersProvider
    ) = GetAllCategoriesUseCase(categoryRepository, dispatchers)

    @Provides
    @Singleton
    fun provideGetCategoryByIdUseCase(
        categoryRepository: CategoryRepository,
        dispatchers: AppDispatchersProvider
    ) = GetCategoryByIdUseCase(categoryRepository, dispatchers)

    @Provides
    @Singleton
    fun provideInsertCategoryUseCase(
        categoryRepository: CategoryRepository,
        dispatchers: AppDispatchersProvider
    ) = InsertCategoryUseCase(categoryRepository, dispatchers)

    @Provides
    @Singleton
    fun provideUpdateCategoryUseCase(
        categoryRepository: CategoryRepository,
        dispatchers: AppDispatchersProvider
    ) = UpdateCategoryUseCase(categoryRepository, dispatchers)

    @Provides
    @Singleton
    fun provideDeleteCategoryUseCase(
        categoryRepository: CategoryRepository,
        dispatchers: AppDispatchersProvider
    ) = DeleteCategoryUseCase(categoryRepository, dispatchers)
} 
