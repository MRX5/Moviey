package com.example.movies.ui.movies.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.movies.model.Movie
import com.example.movies.model.MoviesResponse
import com.example.movies.ui.movies.repo.MoviesRepository
import com.example.movies.ui.movies.repo.MoviesRepositoryImpl
import com.example.movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private var _movies = MutableLiveData<Resource<MoviesResponse>>()
    val movies: LiveData<Resource<MoviesResponse>> get() = _movies

    fun fetchPopularMovies(page: Int) {
        viewModelScope.launch {
            _movies= moviesRepository.getPopularMovies(page) as MutableLiveData<Resource<MoviesResponse>>
        }
    }

    fun fetchUpcomingMovies(page: Int) {
        viewModelScope.launch {
            _movies= moviesRepository.getUpcomingMovies(page) as MutableLiveData<Resource<MoviesResponse>>
        }
    }

    fun fetchTopRatedMovies(page: Int) {
        viewModelScope.launch {
            _movies= moviesRepository.getTopRatedMovies(page) as MutableLiveData<Resource<MoviesResponse>>
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("mostafa", "onCleared: ")
    }
}