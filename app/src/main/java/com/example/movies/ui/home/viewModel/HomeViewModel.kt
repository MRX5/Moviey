package com.example.movies.ui.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.model.response.MoviesResponse
import com.example.movies.ui.home.repo.HomeRepository
import com.example.movies.ui.movies.popular.repo.PopularMoviesRepository
import com.example.movies.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) :
    ViewModel() {

    private var _trendingMovies = MutableStateFlow<DataState<MoviesResponse>>(DataState.Idle)
    val trendingMovies: MutableStateFlow<DataState<MoviesResponse>> get() = _trendingMovies

    private var _upcomingMovies = MutableStateFlow<DataState<MoviesResponse>>(DataState.Idle)
    val upcomingMovies: MutableStateFlow<DataState<MoviesResponse>> get() = _upcomingMovies

    private var _topRatedMovies = MutableStateFlow<DataState<MoviesResponse>>(DataState.Idle)
    val topRatedMovies: MutableStateFlow<DataState<MoviesResponse>> get() = _topRatedMovies

    fun fetchTrendingMovies(page:Int) {
        viewModelScope.launch {
            repository.getTrendingMovies(page).onEach {
                _trendingMovies.value=it
            }.launchIn(viewModelScope)
        }
    }

    fun fetchUpcomingMovies(page: Int) {
        viewModelScope.launch {
            repository.getUpcomingMovies(page).onEach {
                _upcomingMovies.value=it
            }.launchIn(viewModelScope)
        }
    }

    fun fetchTopRatedMovies(page: Int) {
        viewModelScope.launch {
            repository.getTopRatedMovies(page).onEach {
                _topRatedMovies.value=it
            }.launchIn(viewModelScope)
        }
    }
}