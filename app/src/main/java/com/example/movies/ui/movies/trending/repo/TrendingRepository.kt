package com.example.movies.ui.movies.trending.repo

import com.example.movies.model.response.MoviesResponse
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.Flow

interface TrendingRepository {
    suspend fun getTrendingMovies(page:Int): Flow<DataState<MoviesResponse>>
}