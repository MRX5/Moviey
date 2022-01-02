package com.example.movies.ui.movies.movie_details.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.local.entites.MediaEntity
import com.example.movies.data.model.response.MovieDetailsResponse
import com.example.movies.ui.movies.movie_details.repo.MovieDetailsRepository
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
class MovieDetailsViewModel @Inject constructor(private val repository: MovieDetailsRepository) :
    ViewModel() {
    private val _movie = MutableStateFlow<DataState<MovieDetailsResponse>>(DataState.Idle)
    val movie: MutableStateFlow<DataState<MovieDetailsResponse>> get() = _movie

    private val _isFavourite = MutableStateFlow<MediaEntity?>(null)
    val isFavourite: MutableStateFlow<MediaEntity?> get() = _isFavourite

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            repository.getMovieDetails(movieId)
                .onEach {
                    _movie.value=it
                }.map {
                    if (it is DataState.Success) {
                        val data = it.data
                        it.data.year = MediaUtils.extractYearFromDate(data.year)
                        it.data.length = MediaUtils.formatMovieLength(data.length)
                    }
                }.launchIn(viewModelScope)
        }
    }

    fun getFavouriteState(movieID:Int){
        viewModelScope.launch {
            val x=async{  repository.isFavourite(movieID)}
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