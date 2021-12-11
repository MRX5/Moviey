package com.example.movies.network

import com.example.movies.model.response.MovieDetailsResponse
import com.example.movies.model.response.MoviesResponse
import com.example.movies.model.response.SearchResponse
import com.example.movies.model.response.TvDetailsResponse
import retrofit2.Response

interface RemoteDataSource {
    //Movies
    suspend fun getTrendingMovies(page:Int):Response<MoviesResponse>
    suspend fun getPopularMovies(page: Int): Response<MoviesResponse>
    suspend fun getUpcomingMovies(page: Int): Response<MoviesResponse>
    suspend fun getTopRatedMovies(page: Int): Response<MoviesResponse>
    suspend fun getMovieDetails(movieID:Int):Response<MovieDetailsResponse>

    //TV Shows
    suspend fun getTvShowDetails(tvID:Int):Response<TvDetailsResponse>

    //Search
    suspend fun getSearchResults(query:String,page:Int):Response<SearchResponse>

}