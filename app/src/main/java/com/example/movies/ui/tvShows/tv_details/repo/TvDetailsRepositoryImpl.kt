package com.example.movies.ui.tvShows.tv_details.repo

import com.example.movies.data.local.source.LocalDataSource
import com.example.movies.data.local.entites.MediaEntity
import com.example.movies.data.network.RemoteDataSource
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class TvDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) :
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

    override suspend fun isFavourite(tvID: Int)=
        localDataSource.isFavourite(tvID)

    override suspend fun addToFavourites(mediaEntity: MediaEntity) =
        localDataSource.addToFavourites(mediaEntity)

    override suspend fun removeFromFavourites(mediaId: Int) =
        localDataSource.removeFromFavourites(mediaId)

}