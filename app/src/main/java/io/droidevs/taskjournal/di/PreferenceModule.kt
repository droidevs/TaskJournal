package io.droidevs.taskjournal.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.droidevs.taskjournal.data.preference.AppPreferencesPreferenceImpl
import io.droidevs.taskjournal.data.preference.MaxEndCountPreferenceImpl
import io.droidevs.taskjournal.data.preference.MaxFrequencyPreferenceImpl
import io.droidevs.taskjournal.data.preference.ThemePreferenceImpl
import io.droidevs.taskjournal.data.preference.SortOrderPreferenceImpl
import io.droidevs.taskjournal.data.preference.ShowCompletedPreferenceImpl
import io.droidevs.taskjournal.domain.preference.AppPreferencesPreference
import io.droidevs.taskjournal.domain.preference.MaxEndCountPreference
import io.droidevs.taskjournal.domain.preference.MaxFrequencyPreference
import io.droidevs.taskjournal.domain.preference.ThemePreference
import io.droidevs.taskjournal.domain.preference.SortOrderPreference
import io.droidevs.taskjournal.domain.preference.ShowCompletedPreference
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Provides
    @Singleton
    fun provideAppPreferencesPreference(
        appPreferencesPreferenceImpl: AppPreferencesPreferenceImpl
    ): AppPreferencesPreference = appPreferencesPreferenceImpl

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

    @Provides
    @Singleton
    fun provideMaxFrequencyPreference(
        maxFrequencyPreferenceImpl: MaxFrequencyPreferenceImpl
    ): MaxFrequencyPreference = maxFrequencyPreferenceImpl

    @Provides
    @Singleton
    fun provideMaxEndCountPreference(
        maxEndCountPreferenceImpl: MaxEndCountPreferenceImpl
    ): MaxEndCountPreference = maxEndCountPreferenceImpl
} 
