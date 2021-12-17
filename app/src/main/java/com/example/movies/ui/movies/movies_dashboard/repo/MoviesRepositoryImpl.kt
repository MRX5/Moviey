package com.example.movies.ui.movies.movies_dashboard.repo

import com.example.movies.data.network.RemoteDataSource
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class MoviesRepositoryImpl(private val remoteDataSource: RemoteDataSource):MoviesRepository {

    override suspend fun getInTheaterMovies(page:Int) =
        flow {
            emit(DataState.Loading)
            try {
                val response = remoteDataSource.getInTheaterMovies(page)
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

    override suspend fun getTrendingMovies(page:Int) =
        flow {
            emit(DataState.Loading)
            try {
                val response = remoteDataSource.getTrendingMovies(page)
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

    override suspend fun getPopularMovies(page:Int) =
        flow {
            emit(DataState.Loading)
            try {
                val response = remoteDataSource.getPopularMovies(page)
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

    override suspend fun getUpcomingMovies(page:Int) =
        flow {
            emit(DataState.Loading)
            try {
                val response = remoteDataSource.getUpcomingMovies(page)
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

    override suspend fun getTopRatedMovies(page:Int) =
        flow {
            emit(DataState.Loading)
            try {
                val response = remoteDataSource.getTopRatedMovies(page)
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