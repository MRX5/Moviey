package com.example.movies.ui.movies.top_rated.repo

import com.example.movies.data.model.response.MoviesResponse
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.Flow

interface TopRatedMoviesRepository {
    suspend fun getTopRatedMovies(page:Int): Flow<DataState<MoviesResponse>>

}