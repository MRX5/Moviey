package com.example.movies.ui.movies.popular.repo

import com.example.movies.network.RemoteDataSource
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class PopularMoviesRepositoryImpl(private val remoteDataSource: RemoteDataSource) :PopularMoviesRepository{
    override suspend fun getPopularMovies(page: Int) =
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
}