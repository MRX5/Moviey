package com.example.movies.ui.movies.movies_dashboard.repo

import com.example.movies.data.model.response.MoviesResponse
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun getInTheaterMovies(page:Int): Flow<DataState<MoviesResponse>>
    suspend fun getTrendingMovies(page:Int): Flow<DataState<MoviesResponse>>
    suspend fun getPopularMovies(page:Int): Flow<DataState<MoviesResponse>>
    suspend fun getUpcomingMovies(page:Int): Flow<DataState<MoviesResponse>>
    suspend fun getTopRatedMovies(page:Int): Flow<DataState<MoviesResponse>>
}