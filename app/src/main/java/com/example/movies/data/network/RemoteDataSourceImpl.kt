package com.example.movies.data.network

import com.example.movies.data.model.response.*
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService):RemoteDataSource {

    // ------------------------------- Movies --------------------------------

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

    // ------------------------------- TvShows --------------------------------

    override suspend fun getOnTheAirTvShows(page: Int): Response<TvShowsResponse> =
        apiService.getOnTheAirTvShows(page)

    override suspend fun getTrendingTvShows(page: Int): Response<TvShowsResponse> =
        apiService.getTrendingTvShows(page)

    override suspend fun getPopularTvShows(page: Int): Response<TvShowsResponse> =
        apiService.getPopularTvShows(page)

    override suspend fun getTopRatedTvShows(page: Int): Response<TvShowsResponse> =
        apiService.getTopRatedTvShows(page)

    override suspend fun getTvShowDetails(tvID: Int): Response<TvDetailsResponse> =
        apiService.getTvShowDetails(tvID)

    // ------------------------------- TvShows And Movies --------------------------------

    override suspend fun getTrendingMoviesAndTvShows(page: Int): Response<MediaResponse> =
        apiService.getTrendingMoviesAndTvShows(page)

    // ------------------------------- Search --------------------------------

    override suspend fun getSearchResults(query: String,page:Int): Response<MediaResponse> =
        apiService.search(query,page)
}