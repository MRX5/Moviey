package com.example.movies.ui.movies.movies_dashboard.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class MoviesViewModel @Inject constructor(private val repository: MoviesRepository): ViewModel() {

    private var _inTheater = MutableStateFlow<DataState<MoviesResponse>>(DataState.Idle)
    val inTheater: MutableStateFlow<DataState<MoviesResponse>> get() = _inTheater

    private var _trending = MutableStateFlow<DataState<MoviesResponse>>(DataState.Idle)
    val trending: MutableStateFlow<DataState<MoviesResponse>> get() = _trending

    private var _popular = MutableStateFlow<DataState<MoviesResponse>>(DataState.Idle)
    val popular: MutableStateFlow<DataState<MoviesResponse>> get() = _popular

    private var _upcoming = MutableStateFlow<DataState<MoviesResponse>>(DataState.Idle)
    val upcoming: MutableStateFlow<DataState<MoviesResponse>> get() = _upcoming

    private var _topRated = MutableStateFlow<DataState<MoviesResponse>>(DataState.Idle)
    val topRated: MutableStateFlow<DataState<MoviesResponse>> get() = _topRated

    fun fetchInTheaterMovies(page:Int) {
        viewModelScope.launch {
            repository.getInTheaterMovies(page).onEach {
                _inTheater.value=it
            }.launchIn(viewModelScope)
        }
    }

    fun fetchTrendingMovies(page:Int) {
        viewModelScope.launch {
            repository.getTrendingMovies(page).onEach {
                _trending.value=it
            }.launchIn(viewModelScope)
        }
    }

    fun fetchPopularMovies(page: Int) {
        viewModelScope.launch {
            repository.getPopularMovies(page).onEach {
                _popular.value=it
            }.launchIn(viewModelScope)
        }
    }

    fun fetchUpcomingMovies(page: Int) {
        viewModelScope.launch {
            repository.getUpcomingMovies(page).onEach {
                _upcoming.value=it
            }.launchIn(viewModelScope)
        }
    }

    fun fetchTopRatedMovies(page: Int) {
        viewModelScope.launch {
            repository.getTopRatedMovies(page).onEach {
                _topRated.value=it
            }.launchIn(viewModelScope)
        }
    }
}