package com.example.movies.network

import com.example.movies.model.MoviesResponse
import com.example.movies.utils.Constants
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService):RemoteDataSource {
    override suspend fun getPopularMovies(page: Int): Response<MoviesResponse> =
        apiService.getPopularMovies(Constants.API_KEY,page)

    override suspend fun getUpcomingMovies(page: Int): Response<MoviesResponse> =
        apiService.getUpcomingMovies(Constants.API_KEY,page)


    override suspend fun getTopRatedMovies(page: Int): Response<MoviesResponse> =
        apiService.getTopRatedMovies(Constants.API_KEY,page)

}