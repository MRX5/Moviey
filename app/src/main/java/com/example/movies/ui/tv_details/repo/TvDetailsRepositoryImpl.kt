package com.example.movies.ui.tv_details.repo

import android.util.Log
import com.example.movies.model.response.TvDetailsResponse
import com.example.movies.network.RemoteDataSource
import com.example.movies.utils.DataState
import com.example.movies.utils.MediaUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.Exception
import javax.inject.Inject

class TvDetailsRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    TvDetailsRepository {

    override suspend fun getTvShowDetails(tvID: Int) = flow {
        emit(DataState.Loading)
        try {
            val response = remoteDataSource.getTvShowDetails(tvID)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(DataState.Success(it))
                }
            }else{
                emit(DataState.Error(response.message()))
            }
        }catch (ex:Exception){
            emit(DataState.Error("Check internet connection"))
        }
    }
}