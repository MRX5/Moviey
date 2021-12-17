package com.example.movies.ui.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.model.response.MediaResponse
import com.example.movies.data.model.response.MoviesResponse
import com.example.movies.data.model.response.TvShowsResponse
import com.example.movies.ui.home.repo.HomeRepository
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

    private var _trending = MutableStateFlow<DataState<MediaResponse>>(DataState.Idle)
    val trending: MutableStateFlow<DataState<MediaResponse>> get() = _trending

    private var _upcomingMovies = MutableStateFlow<DataState<MoviesResponse>>(DataState.Idle)
    val upcomingMovies: MutableStateFlow<DataState<MoviesResponse>> get() = _upcomingMovies

    private var _popularMovies = MutableStateFlow<DataState<MoviesResponse>>(DataState.Idle)
    val popularMovies: MutableStateFlow<DataState<MoviesResponse>> get() = _popularMovies

    private var _trendingTvShows = MutableStateFlow<DataState<TvShowsResponse>>(DataState.Idle)
    val trendingTvShows: MutableStateFlow<DataState<TvShowsResponse>> get() = _trendingTvShows

    private var _onTheAirTvShows = MutableStateFlow<DataState<TvShowsResponse>>(DataState.Idle)
    val onTheAirTvShows: MutableStateFlow<DataState<TvShowsResponse>> get() = _onTheAirTvShows

    fun fetchUpcomingMovies(page: Int) {
        viewModelScope.launch {
            repository.getUpcomingMovies(page).onEach {
                _upcomingMovies.value=it
            }.launchIn(viewModelScope)
        }
    }
    
    fun fetchTrendingMoviesAndTvShows(page:Int) {
        viewModelScope.launch {
            repository.getTrendingMoviesAndTvShows(page).onEach {
                _trending.value=it
            }.launchIn(viewModelScope)
        }
    }

    fun fetchPopularMovies(page: Int) {
        viewModelScope.launch {
            repository.getPopularMovies(page).onEach {
                _popularMovies.value=it
            }.launchIn(viewModelScope)
        }
    }

    fun fetchTrendingTvShows(page:Int){
        viewModelScope.launch {
            repository.getTrendingTvShows(page).onEach {
                _trendingTvShows.value=it
            }.launchIn(viewModelScope)
        }
    }

    fun fetchOnTheAirTvShows(page:Int){
        viewModelScope.launch {
            repository.getOnTheAirTvShows(page).onEach {
                _onTheAirTvShows.value=it
            }.launchIn(viewModelScope)
        }
    }
}