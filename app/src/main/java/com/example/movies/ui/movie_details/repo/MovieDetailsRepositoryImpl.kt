package com.example.movies.ui.movie_details.repo

import com.example.movies.data.network.RemoteDataSource
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.flow
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