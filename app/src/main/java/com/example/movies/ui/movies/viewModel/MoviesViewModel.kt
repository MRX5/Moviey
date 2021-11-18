package com.example.movies.ui.movies.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.movies.model.Movie
import com.example.movies.ui.movies.repo.MoviesRepository
import com.example.movies.ui.movies.repo.MoviesRepositoryImpl
import com.example.movies.utils.Resource
import com.example.movies.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private var _movies = MutableLiveData<Resource<List<Movie>>>()
    val movies: LiveData<Resource<List<Movie>>> get() = _movies

    fun fetchMovies(page: Int) {
        viewModelScope.launch {
            _movies=moviesRepository.getMovies(page) as MutableLiveData<Resource<List<Movie>>>
        }
    }


    override fun onCleared() {
        super.onCleared()
    }
}