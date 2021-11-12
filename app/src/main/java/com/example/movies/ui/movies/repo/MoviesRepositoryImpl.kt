package com.example.movies.ui.movies.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.movies.model.Movie
import com.example.movies.model.MoviesResponse
import com.example.movies.network.RemoteDataSource
import com.example.movies.utils.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    MoviesRepository {
    override suspend fun getPopularMovies(page: Int): LiveData<Resource<MoviesResponse>> =
        liveData {
            emit(Resource.loading(null))
            try {
                val response = remoteDataSource.getPopularMovies(page)
                if (response.isSuccessful) {
                    emit(Resource.success(response.body()))
                } else {
                    emit(Resource.error(response.message().toString(), null))
                }
            } catch (e: Exception) {
                emit(Resource.error("No internet connection", null))
            }
        }

    override suspend fun getUpcomingMovies(page: Int): LiveData<Resource<MoviesResponse>> =
        liveData {
            emit(Resource.loading(null))
            try {
                val response = remoteDataSource.getUpcomingMovies(page)
                if (response.isSuccessful) {
                    emit(Resource.success(response.body()))
                } else {
                    emit(Resource.error(response.message().toString(), null))
                }
            } catch (e: Exception) {
                emit(Resource.error("No internet connection", null))
            }
        }


    override suspend fun getTopRatedMovies(page: Int): LiveData<Resource<MoviesResponse>> =
        liveData {
            emit(Resource.loading(null))
            try {
                val response = remoteDataSource.getTopRatedMovies(page)
                if (response.isSuccessful) {
                    emit(Resource.success(response.body()))
                } else {
                    emit(Resource.error(response.message().toString(), null))
                }
            } catch (e: Exception) {
                emit(Resource.error("No internet connection", null))
            }
        }

}