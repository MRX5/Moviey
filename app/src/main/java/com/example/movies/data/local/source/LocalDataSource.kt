package com.example.movies.data.local.source

import com.example.movies.data.local.entites.MediaEntity

interface LocalDataSource {
    suspend fun addToFavourites(mediaEntity: MediaEntity)
    suspend fun isFavourite(MediaId:Int):MediaEntity
    suspend fun removeFromFavourites(MediaId: Int)
}