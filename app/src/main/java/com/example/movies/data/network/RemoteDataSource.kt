package com.example.movies.data.network

import com.example.movies.data.model.response.*
import retrofit2.Response

interface RemoteDataSource {
    //Movies
    suspend fun getInTheaterMovies(page:Int):Response<MoviesResponse>
    suspend fun getTrendingMovies(page:Int):Response<MoviesResponse>
    suspend fun getPopularMovies(page: Int): Response<MoviesResponse>
    suspend fun getUpcomingMovies(page: Int): Response<MoviesResponse>
    suspend fun getTopRatedMovies(page: Int): Response<MoviesResponse>
    suspend fun getMovieDetails(movieID:Int):Response<MovieDetailsResponse>

    //TV Shows
    suspend fun getOnTheAirTvShows(page:Int):Response<TvShowsResponse>
    suspend fun getTrendingTvShows(page:Int):Response<TvShowsResponse>
    suspend fun getPopularTvShows(page: Int): Response<TvShowsResponse>
    suspend fun getTopRatedTvShows(page: Int): Response<TvShowsResponse>
    suspend fun getTvShowDetails(tvID:Int):Response<TvDetailsResponse>

    //TvShows And Movies
    suspend fun getTrendingMoviesAndTvShows(page:Int):Response<MediaResponse>

    //Search
    suspend fun getSearchResults(query:String,page:Int):Response<MediaResponse>

}