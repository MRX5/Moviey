package com.example.movies.ui.movies.trending.repo

import com.example.movies.network.RemoteDataSource
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class TrendingRepositoryImpl(private val remoteDataSource: RemoteDataSource) :TrendingRepository{
    override suspend fun getTrendingMovies(page: Int) =
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
}