package com.example.movies.ui.movies.in_theater.repo

import com.example.movies.data.model.response.MoviesResponse
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.Flow

interface InTheaterMoviesRepository {
    suspend fun getInTheaterMovies(page:Int): Flow<DataState<MoviesResponse>>
}