package com.example.movies.network

import com.example.movies.model.response.MovieDetailsResponse
import com.example.movies.model.response.MoviesResponse
import com.example.movies.model.response.SearchResponse
import com.example.movies.model.response.TvDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(@Query("page") page:Int):Response<MoviesResponse>

    @GET("movie/popular")
     suspend fun getPopularMovies(@Query("page") page:Int): Response<MoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page:Int):Response<MoviesResponse>

    @GET("movie/top_rated")
     suspend fun getTopRatedMovies(@Query("page") page:Int):Response<MoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieID:Int,
        @Query("append_to_response") pr:List<String> = listOf("credits,videos,recommendations"))
    :Response<MovieDetailsResponse>

    @GET("search/multi")
    suspend fun search(@Query("query")query:String,@Query("page")page:Int):Response<SearchResponse>

    @GET("tv/{tv_id}")
    suspend fun getTvShowDetails(
        @Path("tv_id") tvID:Int,
        @Query("append_to_response") pr:List<String> = listOf("credits,videos,recommendations"))
            :Response<TvDetailsResponse>

}