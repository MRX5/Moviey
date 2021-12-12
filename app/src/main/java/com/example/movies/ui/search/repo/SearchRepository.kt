package com.example.movies.ui.search.repo

import com.example.movies.model.response.MediaResponse
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun search(query:String,page:Int):Flow<DataState<MediaResponse>>
}