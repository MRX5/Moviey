package com.example.movies.network

import com.example.movies.model.MovieDetailsResponse
import com.example.movies.model.MoviesResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService):RemoteDataSource {
    override suspend fun getPopularMovies(page: Int): Response<MoviesResponse> =
        apiService.getPopularMovies(page)

    override suspend fun getUpcomingMovies(page: Int): Response<MoviesResponse> =
        apiService.getUpcomingMovies(page)


    override suspend fun getTopRatedMovies(page: Int): Response<MoviesResponse> =
        apiService.getTopRatedMovies(page)

    override suspend fun getMovieDetails(movieID: Int): Response<MovieDetailsResponse> =
        apiService.getMovieDetails(movieID)


}