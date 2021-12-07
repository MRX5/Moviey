package com.example.movies.ui.movies.top_rated.repo

import com.example.movies.model.response.MoviesResponse
import com.example.movies.network.RemoteDataSource
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class TopRatedMoviesRepositoryImpl(private val remoteDataSource: RemoteDataSource):TopRatedMoviesRepository {

    override suspend fun getTopRatedMovies(page: Int) =
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