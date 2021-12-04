package com.example.movies.ui.movies.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.movies.model.response.MoviesResponse
import com.example.movies.network.RemoteDataSource
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    MoviesRepository {

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

    override suspend fun getUpcomingMovies(page: Int) =
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