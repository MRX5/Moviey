package com.example.movies.ui.movies.upcoming.repo

import com.example.movies.model.response.MoviesResponse
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.Flow

interface UpcomingMoviesRepository {
    suspend fun getUpcomingMovies(page:Int): Flow<DataState<MoviesResponse>>
}