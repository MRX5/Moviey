package com.example.movies.ui.favourites.viewModel

import androidx.lifecycle.*
import com.example.movies.data.local.entites.MediaEntity
import com.example.movies.data.model.response.MoviesResponse
import com.example.movies.ui.favourites.repo.FavouritesRepository
import com.example.movies.ui.movies.popular.repo.PopularMoviesRepository
import com.example.movies.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val repository: FavouritesRepository) :
    ViewModel() {

    private var _favourites = MutableStateFlow<DataState<List<MediaEntity>>>(DataState.Idle)
    val favourites: MutableStateFlow<DataState<List<MediaEntity>>> get() = _favourites

    fun fetchFavourites() {
        viewModelScope.launch {
            repository.getFavourites().onEach {
                _favourites.value=it
            }.launchIn(viewModelScope)
        }
    }
}