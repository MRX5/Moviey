package com.example.movies.ui.movies.in_theater.repo

import com.example.movies.network.RemoteDataSource
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class InTheaterMoviesRepositoryImpl(private val remoteDataSource: RemoteDataSource) :InTheaterMoviesRepository{
    override suspend fun getInTheaterMovies(page: Int) =
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
}