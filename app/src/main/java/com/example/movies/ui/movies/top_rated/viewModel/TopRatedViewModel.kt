package com.example.movies.ui.movies.top_rated.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.movies.model.response.MoviesResponse
import com.example.movies.ui.movies.popular.repo.PopularMoviesRepository
import com.example.movies.ui.movies.top_rated.repo.TopRatedMoviesRepository
import com.example.movies.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopRatedViewModel @Inject constructor(private val repository: TopRatedMoviesRepository) :
    ViewModel() {

    private var _movies = MutableStateFlow<DataState<MoviesResponse>>(DataState.Idle)
    val movies: MutableStateFlow<DataState<MoviesResponse>> get() = _movies

    fun fetchTopRatedMovies(page: Int) {
        viewModelScope.launch {
            repository.getTopRatedMovies(page).onEach {
                    _movies.value=it
                }.launchIn(viewModelScope)
        }
    }

}