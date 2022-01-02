package com.example.movies.data.local.source

import com.example.movies.data.local.FavouriteDao
import com.example.movies.data.local.entites.MediaEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val favouriteDao: FavouriteDao):
    LocalDataSource {

    override suspend fun addToFavourites(mediaEntity: MediaEntity) =
        favouriteDao.addToFavourites(mediaEntity)

    override suspend fun isFavourite(MediaId: Int): MediaEntity =
        favouriteDao.isFavourite(MediaId)

    override suspend fun removeFromFavourites(MediaId: Int) =
        favouriteDao.removeFromFavourites(MediaId)

}