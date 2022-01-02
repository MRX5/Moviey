package com.example.movies.ui.tvShows.tv_details.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.local.entites.MediaEntity
import com.example.movies.data.model.response.TvDetailsResponse
import com.example.movies.ui.tvShows.tv_details.repo.TvDetailsRepository
import com.example.movies.utils.DataState
import com.example.movies.utils.MediaUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvDetailsViewModel @Inject constructor(private val repository:TvDetailsRepository) : ViewModel() {
    private val _tvShow = MutableStateFlow<DataState<TvDetailsResponse>>(DataState.Idle)
    val tvShow: MutableStateFlow<DataState<TvDetailsResponse>> get() = _tvShow

    private val _isFavourite = MutableStateFlow<MediaEntity?>(null)
    val isFavourite: MutableStateFlow<MediaEntity?> get() = _isFavourite

    fun getMovieDetails(tvID: Int) {
        viewModelScope.launch {
            repository.getTvShowDetails(tvID)
                .onEach {
                    _tvShow.value = it
                }.map {
                    if (it is DataState.Success) {
                        val data = it.data
                        it.data.year = MediaUtils.extractYearFromDate(data.year)
                    }
                }.launchIn(viewModelScope)
        }
    }

    fun getFavouriteState(tvID:Int){
        viewModelScope.launch {
            val x=async{  repository.isFavourite(tvID)}
            _isFavourite.value=x.await()
        }
    }

    fun addToFavourites(mediaEntity: MediaEntity){
        viewModelScope.launch {
            repository.addToFavourites(mediaEntity)
        }
    }

    fun removeFromFavourites(MediaId:Int){
        viewModelScope.launch {
            repository.removeFromFavourites(MediaId)
        }
    }
}