package com.example.movies.di

import com.example.movies.network.RemoteDataSource
import com.example.movies.ui.details.repo.MovieDetailsRepository
import com.example.movies.ui.details.repo.MovieDetailsRepositoryImpl
import com.example.movies.ui.movies.repo.MoviesRepository
import com.example.movies.ui.movies.repo.MoviesRepositoryImpl
import com.example.movies.ui.search.repo.SearchRepository
import com.example.movies.ui.search.repo.SearchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    fun provideMoviesRepository(remoteDataSource: RemoteDataSource): MoviesRepository =
        MoviesRepositoryImpl(remoteDataSource)

    @Provides
    fun provideMoviesDetailsRepository(remoteDataSource: RemoteDataSource): MovieDetailsRepository =
        MovieDetailsRepositoryImpl(remoteDataSource)

    @Provides
    fun provideSearchRepository(remoteDataSource: RemoteDataSource):SearchRepository=
        SearchRepositoryImpl(remoteDataSource)
}