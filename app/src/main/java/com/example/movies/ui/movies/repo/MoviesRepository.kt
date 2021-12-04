package com.example.movies.ui.movies.repo

import com.example.movies.model.response.MoviesResponse
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun getPopularMovies(page:Int): Flow<DataState<MoviesResponse>>
    suspend fun getUpcomingMovies(page:Int):Flow<DataState<MoviesResponse>>
    suspend fun getTopRatedMovies(page:Int):Flow<DataState<MoviesResponse>>
}