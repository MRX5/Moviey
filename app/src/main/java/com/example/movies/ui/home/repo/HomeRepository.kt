package com.example.movies.ui.home.repo

import com.example.movies.data.model.response.MediaResponse
import com.example.movies.data.model.response.MoviesResponse
import com.example.movies.data.model.response.TvShowsResponse
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
  //  suspend fun getTrendingMovies(page:Int): Flow<DataState<MoviesResponse>>
    suspend fun getUpcomingMovies(page:Int): Flow<DataState<MoviesResponse>>
    suspend fun getPopularMovies(page:Int): Flow<DataState<MoviesResponse>>

    suspend fun getTrendingTvShows(page:Int): Flow<DataState<TvShowsResponse>>
    suspend fun getOnTheAirTvShows(page:Int): Flow<DataState<TvShowsResponse>>

    suspend fun getTrendingMoviesAndTvShows(page:Int) : Flow<DataState<MediaResponse>>
}