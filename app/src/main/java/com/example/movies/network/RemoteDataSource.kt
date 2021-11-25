package com.example.movies.network

import com.example.movies.model.MovieDetailsResponse
import com.example.movies.model.MoviesResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getPopularMovies(page: Int): Response<MoviesResponse>
    suspend fun getUpcomingMovies(page: Int): Response<MoviesResponse>
    suspend fun getTopRatedMovies(page: Int): Response<MoviesResponse>

    suspend fun getMovieDetails(movieID:Int):Response<MovieDetailsResponse>
}