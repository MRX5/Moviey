package com.example.movies.di

import com.example.movies.data.local.FavouriteDao
import com.example.movies.data.local.source.LocalDataSource
import com.example.movies.data.network.RemoteDataSource
import com.example.movies.ui.favourites.repo.FavouritesRepository
import com.example.movies.ui.favourites.repo.FavouritesRepositoryImpl
import com.example.movies.ui.home.repo.HomeRepository
import com.example.movies.ui.home.repo.HomeRepositoryImpl
import com.example.movies.ui.movies.movie_details.repo.MovieDetailsRepository
import com.example.movies.ui.movies.movie_details.repo.MovieDetailsRepositoryImpl
import com.example.movies.ui.movies.repository.MoviesRepository
import com.example.movies.ui.movies.repository.MoviesRepositoryImpl
import com.example.movies.ui.search.repo.SearchRepository
import com.example.movies.ui.search.repo.SearchRepositoryImpl
import com.example.movies.ui.tvShows.repository.TvShowsRepository
import com.example.movies.ui.tvShows.repository.TvShowsRepositoryImpl
import com.example.movies.ui.tvShows.tv_details.repo.TvDetailsRepository
import com.example.movies.ui.tvShows.tv_details.repo.TvDetailsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    fun provideHomeRepository(remoteDataSource: RemoteDataSource): HomeRepository =
        HomeRepositoryImpl(remoteDataSource)

    @Provides
    fun provideMoviesRepository(remoteDataSource: RemoteDataSource): MoviesRepository =
        MoviesRepositoryImpl(remoteDataSource)

    @Provides
    fun provideMoviesDetailsRepository(remoteDataSource: RemoteDataSource,localDataSource: LocalDataSource): MovieDetailsRepository =
        MovieDetailsRepositoryImpl(remoteDataSource,localDataSource)

    @Provides
    fun provideSearchRepository(remoteDataSource: RemoteDataSource):SearchRepository=
        SearchRepositoryImpl(remoteDataSource)

    @Provides
    fun provideTvShowsRepository(remoteDataSource: RemoteDataSource): TvShowsRepository =
        TvShowsRepositoryImpl(remoteDataSource)

    @Provides
    fun provideTvDetailsRepository(remoteDataSource: RemoteDataSource,localDataSource: LocalDataSource):TvDetailsRepository=
        TvDetailsRepositoryImpl(remoteDataSource,localDataSource)

    @Provides
    fun provideFavouritesRepository(favouriteDao: FavouriteDao):FavouritesRepository =
        FavouritesRepositoryImpl(favouriteDao)
}