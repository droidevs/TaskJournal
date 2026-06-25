package io.droidevs.taskjournal.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.droidevs.taskjournal.data.repository.NoteRepositoryImpl
import io.droidevs.taskjournal.data.repository.CategoryRepositoryImpl
import io.droidevs.taskjournal.domain.repository.NoteRepository
import io.droidevs.taskjournal.domain.repository.CategoryRepository
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
} 
