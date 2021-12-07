package com.example.movies.ui.movies.upcoming.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.movies.model.response.MoviesResponse
import com.example.movies.ui.movies.popular.repo.PopularMoviesRepository
import com.example.movies.ui.movies.upcoming.repo.UpcomingMoviesRepository
import com.example.movies.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingViewModel @Inject constructor(private val repository: UpcomingMoviesRepository) :
    ViewModel() {

    private var _movies = MutableStateFlow<DataState<MoviesResponse>>(DataState.Idle)
    val movies: MutableStateFlow<DataState<MoviesResponse>> get() = _movies

    fun fetchUpcomingMovies(page: Int) {
        viewModelScope.launch {
            repository.getUpcomingMovies(page).onEach {
                    _movies.value=it
                }.launchIn(viewModelScope)
        }
    }

}