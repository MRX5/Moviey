package com.example.movies.ui.movies.movies.viewModel

import androidx.lifecycle.*
import com.example.movies.data.model.response.MoviesResponse
import com.example.movies.ui.movies.repository.MoviesRepository
import com.example.movies.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: MoviesRepository) :
    ViewModel() {

    private var _movies = MutableStateFlow<DataState<MoviesResponse>>(DataState.Idle)
    val movies: MutableStateFlow<DataState<MoviesResponse>> get() = _movies

    fun fetchInTheatresMovies(page: Int) {
        viewModelScope.launch {
            repository.getInTheaterMovies(page).onEach {
                _movies.value=it
            }.launchIn(viewModelScope)
        }
    }

    fun fetchTrendingMovies(page: Int) {
        viewModelScope.launch {
            repository.getTrendingMovies(page).onEach {
                _movies.value=it
            }.launchIn(viewModelScope)
        }
    }

    fun fetchPopularMovies(page: Int) {
        viewModelScope.launch {
            repository.getPopularMovies(page).onEach {
                    _movies.value=it
                }.launchIn(viewModelScope)
        }
    }

    fun fetchUpcomingMovies(page: Int) {
        viewModelScope.launch {
            repository.getUpcomingMovies(page).onEach {
                _movies.value=it
            }.launchIn(viewModelScope)
        }
    }
    fun fetchTopRatedMovies(page: Int) {
        viewModelScope.launch {
            repository.getTopRatedMovies(page).onEach {
                _movies.value=it
            }.launchIn(viewModelScope)
        }
    }
}