package com.example.movies.di

import com.example.movies.network.RemoteDataSource
import com.example.movies.ui.home.repo.HomeRepository
import com.example.movies.ui.home.repo.HomeRepositoryImpl
import com.example.movies.ui.movie_details.repo.MovieDetailsRepository
import com.example.movies.ui.movie_details.repo.MovieDetailsRepositoryImpl
import com.example.movies.ui.movies.in_theater.repo.InTheaterMoviesRepository
import com.example.movies.ui.movies.in_theater.repo.InTheaterMoviesRepositoryImpl
import com.example.movies.ui.movies.movies_dashboard.repo.MoviesRepository
import com.example.movies.ui.movies.movies_dashboard.repo.MoviesRepositoryImpl
import com.example.movies.ui.movies.popular.repo.PopularMoviesRepository
import com.example.movies.ui.movies.popular.repo.PopularMoviesRepositoryImpl
import com.example.movies.ui.movies.top_rated.repo.TopRatedMoviesRepository
import com.example.movies.ui.movies.top_rated.repo.TopRatedMoviesRepositoryImpl
import com.example.movies.ui.movies.trending.repo.TrendingRepository
import com.example.movies.ui.movies.trending.repo.TrendingRepositoryImpl
import com.example.movies.ui.movies.upcoming.repo.UpcomingMoviesRepository
import com.example.movies.ui.movies.upcoming.repo.UpcomingMoviesRepositoryImpl
import com.example.movies.ui.search.repo.SearchRepository
import com.example.movies.ui.search.repo.SearchRepositoryImpl
import com.example.movies.ui.tv_details.repo.TvDetailsRepository
import com.example.movies.ui.tv_details.repo.TvDetailsRepositoryImpl
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
    fun provideMoviesRepository(remoteDataSource: RemoteDataSource):MoviesRepository=
        MoviesRepositoryImpl(remoteDataSource)

    @Provides
    fun provideInTheaterMoviesRepository(remoteDataSource: RemoteDataSource):InTheaterMoviesRepository=
        InTheaterMoviesRepositoryImpl(remoteDataSource)

    @Provides
    fun provideTrendingMoviesRepository(remoteDataSource: RemoteDataSource):TrendingRepository=
        TrendingRepositoryImpl(remoteDataSource)

    @Provides
    fun providePopularMoviesRepository(remoteDataSource: RemoteDataSource): PopularMoviesRepository =
        PopularMoviesRepositoryImpl(remoteDataSource)

    @Provides
    fun provideUpcomingMoviesRepository(remoteDataSource: RemoteDataSource): UpcomingMoviesRepository =
        UpcomingMoviesRepositoryImpl(remoteDataSource)

    @Provides
    fun provideTopRatedMoviesRepository(remoteDataSource: RemoteDataSource): TopRatedMoviesRepository =
        TopRatedMoviesRepositoryImpl(remoteDataSource)

    @Provides
    fun provideMoviesDetailsRepository(remoteDataSource: RemoteDataSource): MovieDetailsRepository =
        MovieDetailsRepositoryImpl(remoteDataSource)

    @Provides
    fun provideSearchRepository(remoteDataSource: RemoteDataSource):SearchRepository=
        SearchRepositoryImpl(remoteDataSource)

    @Provides
    fun provideTvDetailsRepository(remoteDataSource: RemoteDataSource):TvDetailsRepository=
        TvDetailsRepositoryImpl(remoteDataSource)
}