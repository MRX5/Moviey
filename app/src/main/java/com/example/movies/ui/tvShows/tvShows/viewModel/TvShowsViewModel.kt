package com.example.movies.ui.tvShows.tvShows.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.model.response.TvShowsResponse
import com.example.movies.ui.tvShows.tvShowsDashboard.repo.TvShowsDashboardRepository
import com.example.movies.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel@Inject constructor(private val repository: TvShowsDashboardRepository):ViewModel() {


    private var _tvShows = MutableStateFlow<DataState<TvShowsResponse>>(DataState.Idle)
    val tvShows: MutableStateFlow<DataState<TvShowsResponse>> get() = _tvShows

    fun fetchOnTheAirTvShows(page:Int) {
        viewModelScope.launch {
            repository.getOnTheAirTvShows(page).onEach {
                _tvShows.value=it
            }.launchIn(viewModelScope)
        }
    }

    fun fetchTrendingTvShows(page:Int) {
        viewModelScope.launch {
            repository.getTrendingTvShows(page).onEach {
                _tvShows.value=it
            }.launchIn(viewModelScope)
        }
    }

    fun fetchPopularTvShows(page: Int) {
        viewModelScope.launch {
            repository.getPopularTvShows(page).onEach {
                _tvShows.value=it
            }.launchIn(viewModelScope)
        }
    }

    fun fetchTopRatedTvShows(page: Int) {
        viewModelScope.launch {
            repository.getTopRatedTvShows(page).onEach {
                _tvShows.value=it
            }.launchIn(viewModelScope)
        }
    }

}