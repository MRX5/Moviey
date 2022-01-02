package com.example.movies.ui.movies.movie_details.repo

import com.example.movies.data.local.entites.MediaEntity
import com.example.movies.data.model.response.MovieDetailsResponse
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
    suspend fun getMovieDetails(movieId:Int): Flow<DataState<MovieDetailsResponse>>
    suspend fun isFavourite(movieID: Int):MediaEntity
    suspend fun addToFavourites(mediaEntity: MediaEntity)
    suspend fun removeFromFavourites(mediaId:Int)
}