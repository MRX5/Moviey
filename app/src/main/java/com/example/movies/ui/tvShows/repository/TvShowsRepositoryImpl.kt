package com.example.movies.ui.tvShows.repository

import com.example.movies.data.network.RemoteDataSource
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class TvShowsRepositoryImpl(private val remoteDataSource: RemoteDataSource): TvShowsRepository {

    override suspend fun getOnTheAirTvShows(page:Int) =
        flow {
            emit(DataState.Loading)
            try {
                val response = remoteDataSource.getOnTheAirTvShows(page)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(DataState.Success(it))
                    }
                } else {
                    emit(DataState.Error(response.message().toString()))
                }
            } catch (e: Exception) {
                emit(DataState.Error("No internet connection"))
            }
        }

    override suspend fun getTrendingTvShows(page:Int) =
        flow {
            emit(DataState.Loading)
            try {
                val response = remoteDataSource.getTrendingTvShows(page)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(DataState.Success(it))
                    }
                } else {
                    emit(DataState.Error(response.message().toString()))
                }
            } catch (e: Exception) {
                emit(DataState.Error("No internet connection"))
            }
        }

    override suspend fun getPopularTvShows(page:Int) =
        flow {
            emit(DataState.Loading)
            try {
                val response = remoteDataSource.getPopularTvShows(page)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(DataState.Success(it))
                    }
                } else {
                    emit(DataState.Error(response.message().toString()))
                }
            } catch (e: Exception) {
                emit(DataState.Error("No internet connection"))
            }
        }

    override suspend fun getTopRatedTvShows(page:Int) =
        flow {
            emit(DataState.Loading)
            try {
                val response = remoteDataSource.getTopRatedTvShows(page)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(DataState.Success(it))
                    }
                } else {
                    emit(DataState.Error(response.message().toString()))
                }
            } catch (e: Exception) {
                emit(DataState.Error("No internet connection"))
            }
        }
}