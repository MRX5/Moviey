package com.example.movies.di

import com.example.movies.ui.movies.repo.MoviesRepository
import com.example.movies.ui.movies.repo.MoviesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    fun provideMoviesRepository(): MoviesRepository = MoviesRepositoryImpl()
}