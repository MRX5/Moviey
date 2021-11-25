package com.example.movies.ui.details.repo

import com.example.movies.model.MovieDetailsResponse
import com.example.movies.network.RemoteDataSource
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource)
    :MovieDetailsRepository {

    override suspend fun getMovieDetails(movieId: Int) =
        flow {
            emit(DataState.Loading)
            try {
                val response = remoteDataSource.getMovieDetails(movieId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(DataState.Success(it))
                    } ?: emit(DataState.Error("Check internet connection"))
                } else {
                    emit(DataState.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(DataState.Error(e.message.toString()))
            }
        }
}