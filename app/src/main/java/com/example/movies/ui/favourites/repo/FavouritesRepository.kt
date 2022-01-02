package com.example.movies.ui.favourites.repo

import com.example.movies.data.local.entites.MediaEntity
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {
    suspend fun getFavourites():Flow<DataState<List<MediaEntity>>>
}