package com.example.movies.ui.movies.movie_details.repo

import com.example.movies.data.local.source.LocalDataSource
import com.example.movies.data.local.entites.MediaEntity
import com.example.movies.data.network.RemoteDataSource
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
)
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

    override suspend fun isFavourite(movieID: Int)=
        localDataSource.isFavourite(movieID)

    override suspend fun addToFavourites(mediaEntity: MediaEntity) =
        localDataSource.addToFavourites(mediaEntity)

    override suspend fun removeFromFavourites(mediaId: Int) =
        localDataSource.removeFromFavourites(mediaId)
}