package com.example.movies.ui.home.repo

import com.example.movies.model.response.MoviesResponse
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getTrendingMovies(page:Int): Flow<DataState<MoviesResponse>>
    suspend fun getUpcomingMovies(page:Int): Flow<DataState<MoviesResponse>>
    suspend fun getPopularMovies(page:Int): Flow<DataState<MoviesResponse>>
}