package com.example.movies.ui.movies.trending.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.movies.model.response.MoviesResponse
import com.example.movies.ui.movies.popular.repo.PopularMoviesRepository
import com.example.movies.ui.movies.trending.repo.TrendingRepository
import com.example.movies.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(private val repository: TrendingRepository) :
    ViewModel() {

    private var _movies = MutableStateFlow<DataState<MoviesResponse>>(DataState.Idle)
    val movies: MutableStateFlow<DataState<MoviesResponse>> get() = _movies

    fun fetchTrendingMovies(page: Int) {
        viewModelScope.launch {
            repository.getTrendingMovies(page).onEach {
                    _movies.value=it
                }.launchIn(viewModelScope)
        }
    }
}