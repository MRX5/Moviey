package com.example.movies.ui.tvShows.repository

import com.example.movies.data.model.response.TvShowsResponse
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.Flow

interface TvShowsRepository {
    suspend fun getOnTheAirTvShows(page:Int): Flow<DataState<TvShowsResponse>>
    suspend fun getTrendingTvShows(page:Int): Flow<DataState<TvShowsResponse>>
    suspend fun getPopularTvShows(page:Int): Flow<DataState<TvShowsResponse>>
    suspend fun getTopRatedTvShows(page:Int): Flow<DataState<TvShowsResponse>>
}