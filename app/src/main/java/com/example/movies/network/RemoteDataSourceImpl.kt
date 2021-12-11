package com.example.movies.network

import com.example.movies.model.response.MovieDetailsResponse
import com.example.movies.model.response.MoviesResponse
import com.example.movies.model.response.SearchResponse
import com.example.movies.model.response.TvDetailsResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService):RemoteDataSource {

    override suspend fun getInTheaterMovies(page: Int): Response<MoviesResponse> =
        apiService.getInTheaterMovies(page)

    override suspend fun getTrendingMovies(page: Int): Response<MoviesResponse> =
        apiService.getTrendingMovies(page)

    override suspend fun getPopularMovies(page: Int): Response<MoviesResponse> =
        apiService.getPopularMovies(page)

    override suspend fun getUpcomingMovies(page: Int): Response<MoviesResponse> =
        apiService.getUpcomingMovies(page)


    override suspend fun getTopRatedMovies(page: Int): Response<MoviesResponse> =
        apiService.getTopRatedMovies(page)

    override suspend fun getMovieDetails(movieID: Int): Response<MovieDetailsResponse> =
        apiService.getMovieDetails(movieID)
    //----------------------------------------------------------

    override suspend fun getTvShowDetails(tvID: Int): Response<TvDetailsResponse> =
        apiService.getTvShowDetails(tvID)
    //----------------------------------------------------------

    override suspend fun getSearchResults(query: String,page:Int): Response<SearchResponse> =
        apiService.search(query,page)
}