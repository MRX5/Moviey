package com.example.movies.ui.search.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.model.response.MediaResponse
import com.example.movies.ui.search.repo.SearchRepository
import com.example.movies.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository):ViewModel() {
    private val _media = MutableStateFlow<DataState<MediaResponse>>(DataState.Idle)
    val media: MutableStateFlow<DataState<MediaResponse>> get() = _media

    fun search(query:String,page:Int){
        viewModelScope.launch {
            repository.search(query,page)
                .onEach {
                    _media.value=it
                }.launchIn(viewModelScope)
        }
    }
}