package com.example.movies.ui.details.repo

import com.example.movies.model.response.MovieDetailsResponse
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
    suspend fun getMovieDetails(movieId:Int): Flow<DataState<MovieDetailsResponse>>
}