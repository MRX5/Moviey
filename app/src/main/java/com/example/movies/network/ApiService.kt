package com.example.movies.network

import com.example.movies.model.response.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // ------------------------------- Movies --------------------------------

    @GET("trending/movie/week")
    suspend fun getTrendingMovies(@Query("page") page:Int):Response<MoviesResponse>

    @GET("movie/now_playing")
    suspend fun getInTheaterMovies(@Query("page") page:Int):Response<MoviesResponse>
    
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

    // ------------------------------- TvShows --------------------------------

    @GET("trending/tv/week")
    suspend fun getTrendingTvShows(@Query("page") page:Int):Response<TvShowsResponse>

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTvShows(@Query("page") page:Int):Response<TvShowsResponse>

    @GET("tv/popular")
    suspend fun getPopularTvShows(@Query("page") page:Int): Response<TvShowsResponse>

    @GET("tv/upcoming")
    suspend fun getUpcomingTvShows(@Query("page") page:Int):Response<TvShowsResponse>

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(@Query("page") page:Int):Response<TvShowsResponse>

    @GET("tv/{tv_id}")
    suspend fun getTvShowDetails(
        @Path("tv_id") tvID:Int,
        @Query("append_to_response") pr:List<String> = listOf("credits,videos,recommendations"))
            :Response<TvDetailsResponse>

    // ------------------------------- TvShows And Movies --------------------------------

    @GET("trending/all/week")
    suspend fun getTrendingMoviesAndTvShows(@Query("page") page:Int):Response<MediaResponse>

    // ------------------------------- Search --------------------------------

    @GET("search/multi")
    suspend fun search(@Query("query")query:String,@Query("page")page:Int):Response<MediaResponse>

}