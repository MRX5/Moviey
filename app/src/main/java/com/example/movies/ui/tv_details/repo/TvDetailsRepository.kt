package com.example.movies.ui.tv_details.repo

import com.example.movies.data.model.response.TvDetailsResponse
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.Flow

interface TvDetailsRepository {
    suspend fun getTvShowDetails(tvID:Int): Flow<DataState<TvDetailsResponse>>
}