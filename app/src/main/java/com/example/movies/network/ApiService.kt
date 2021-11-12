package com.example.movies.network

import com.example.movies.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
     suspend fun getPopularMovies(@Query("api_key") api_key:String, @Query("page") page:Int): Response<MoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") api_key:String, @Query("page") page:Int):Response<MoviesResponse>

    @GET("movie/top_rated")
     suspend fun getTopRatedMovies(@Query("api_key") api_key:String, @Query("page") page:Int):Response<MoviesResponse>
}