package com.example.movies.ui.search.repo

import com.example.movies.data.model.response.MediaResponse
import com.example.movies.data.network.RemoteDataSource
import com.example.movies.utils.DataState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    SearchRepository {

    @FlowPreview
    override suspend fun search(query: String, page:Int): Flow<DataState<MediaResponse>> =
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
        }.debounce(2000)
}