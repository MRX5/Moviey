package com.example.movies.ui.search.repo

import com.example.movies.model.response.SearchResponse
import com.example.movies.network.RemoteDataSource
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    SearchRepository {

    override suspend fun search(query: String,page:Int): Flow<DataState<SearchResponse>> =
        flow {
            emit(DataState.Loading)
            try {
                val response=remoteDataSource.getSearchResults(query,page)
                if(response.isSuccessful){
                    response.body()?.let {
                        emit(DataState.Success(it))
                    }?:emit(DataState.Error("Check internet connection"))
                }
                else{
                    emit(DataState.Error("Check internet connection"))
                }
            }catch (ex:Exception){
                emit(DataState.Error(ex.message.toString()))
            }
        }
}