package com.example.movies.ui.favourites.repo

import com.example.movies.data.local.FavouriteDao
import com.example.movies.data.local.entites.MediaEntity
import com.example.movies.data.network.RemoteDataSource
import com.example.movies.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FavouritesRepositoryImpl(private val dao: FavouriteDao) :FavouritesRepository{
    override suspend fun getFavourites()= flow {
        emit(DataState.Loading)
        val data=dao.getFavourites()
        emit(DataState.Success(data))
    }.flowOn(Dispatchers.IO)
}