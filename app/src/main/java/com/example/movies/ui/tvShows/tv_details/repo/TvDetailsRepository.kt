package com.example.movies.ui.tvShows.tv_details.repo

import com.example.movies.data.local.entites.MediaEntity
import com.example.movies.data.model.response.TvDetailsResponse
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.Flow

interface TvDetailsRepository {
    suspend fun getTvShowDetails(tvID:Int): Flow<DataState<TvDetailsResponse>>
    suspend fun isFavourite(tvID: Int):MediaEntity
    suspend fun addToFavourites(mediaEntity: MediaEntity)
    suspend fun removeFromFavourites(mediaId:Int)
}