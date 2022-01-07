package com.example.movies.ui.tvShows.tvShowsDashboard.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.model.response.TvShowsResponse
import com.example.movies.ui.tvShows.repository.TvShowsRepository
import com.example.movies.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowsDashboardViewModel @Inject constructor(private val repository: TvShowsRepository):ViewModel(){

    private var _onTheAir = MutableStateFlow<DataState<TvShowsResponse>>(DataState.Idle)
    val onTheAir: MutableStateFlow<DataState<TvShowsResponse>> get() = _onTheAir

    private var _trending = MutableStateFlow<DataState<TvShowsResponse>>(DataState.Idle)
    val trending: MutableStateFlow<DataState<TvShowsResponse>> get() = _trending

    private var _popular = MutableStateFlow<DataState<TvShowsResponse>>(DataState.Idle)
    val popular: MutableStateFlow<DataState<TvShowsResponse>> get() = _popular

    private var _topRated = MutableStateFlow<DataState<TvShowsResponse>>(DataState.Idle)
    val topRated: MutableStateFlow<DataState<TvShowsResponse>> get() = _topRated


    fun fetchOnTheAirTvShows(page:Int) {
        viewModelScope.launch {
            repository.getOnTheAirTvShows(page).onEach {
                _onTheAir.value=it
            }.launchIn(viewModelScope)
        }
    }

    fun fetchTrendingTvShows(page:Int) {
        viewModelScope.launch {
            repository.getTrendingTvShows(page).onEach {
                _trending.value=it
            }.launchIn(viewModelScope)
        }
    }

    fun fetchPopularTvShows(page: Int) {
        viewModelScope.launch {
            repository.getPopularTvShows(page).onEach {
                _popular.value=it
            }.launchIn(viewModelScope)
        }
    }

    fun fetchTopRatedTvShows(page: Int) {
        viewModelScope.launch {
            repository.getTopRatedTvShows(page).onEach {
                _topRated.value=it
            }.launchIn(viewModelScope)
        }
    }
}