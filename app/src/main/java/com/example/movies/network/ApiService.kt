package com.example.movies.network

import com.example.movies.model.MovieDetailsResponse
import com.example.movies.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
     suspend fun getPopularMovies(@Query("page") page:Int): Response<MoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page:Int):Response<MoviesResponse>

    @GET("movie/top_rated")
     suspend fun getTopRatedMovies(@Query("page") page:Int):Response<MoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieID:Int, @Query("append_to_response") credits:String="credits")
                :Response<MovieDetailsResponse>


}