package io.droidevs.taskjournal.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.droidevs.taskjournal.data.preference.ThemePreferenceImpl
import io.droidevs.taskjournal.data.preference.SortOrderPreferenceImpl
import io.droidevs.taskjournal.data.preference.ShowCompletedPreferenceImpl
import io.droidevs.taskjournal.domain.preference.ThemePreference
import io.droidevs.taskjournal.domain.preference.SortOrderPreference
import io.droidevs.taskjournal.domain.preference.ShowCompletedPreference
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Provides
    @Singleton
    fun provideThemePreference(
        themePreferenceImpl: ThemePreferenceImpl
    ): ThemePreference = themePreferenceImpl

    @Provides
    @Singleton
    fun provideSortOrderPreference(
        sortOrderPreferenceImpl: SortOrderPreferenceImpl
    ): SortOrderPreference = sortOrderPreferenceImpl

    @Provides
    @Singleton
    fun provideShowCompletedPreference(
        showCompletedPreferenceImpl: ShowCompletedPreferenceImpl
    ): ShowCompletedPreference = showCompletedPreferenceImpl
} 
