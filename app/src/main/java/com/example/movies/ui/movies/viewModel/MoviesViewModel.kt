package com.example.movies.ui.movies.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.movies.model.response.MoviesResponse
import com.example.movies.ui.movies.repo.MoviesRepository
import com.example.movies.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private var _movies = MutableStateFlow<DataState<MoviesResponse>>(DataState.Idle)
    val movies: MutableStateFlow<DataState<MoviesResponse>> get() = _movies

    fun fetchPopularMovies(page: Int) {
        viewModelScope.launch {
            moviesRepository.getPopularMovies(page).onEach {
                    _movies.value=it
                }.launchIn(viewModelScope)
        }
    }

    fun fetchUpcomingMovies(page: Int) {
        viewModelScope.launch {
            viewModelScope.launch {
                moviesRepository.getUpcomingMovies(page).onEach {
                    _movies.value=it
                }.launchIn(viewModelScope)
            }
        }
    }

    fun fetchTopRatedMovies(page: Int) {
        viewModelScope.launch {
            viewModelScope.launch {
                moviesRepository.getTopRatedMovies(page).onEach {
                    _movies.value=it
                }.launchIn(viewModelScope)
            }
        }
    }


}