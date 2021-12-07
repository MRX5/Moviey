package com.example.movies.ui.movies.popular.repo

import com.example.movies.model.response.MoviesResponse
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.Flow

interface PopularMoviesRepository {
    suspend fun getPopularMovies(page:Int): Flow<DataState<MoviesResponse>>
}