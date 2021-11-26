package com.example.movies.ui.details.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.model.MovieDetailsResponse
import com.example.movies.ui.details.repo.MovieDetailsRepository
import com.example.movies.utils.DataState
import com.example.movies.utils.MovieConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val repository: MovieDetailsRepository) :
    ViewModel() {
    private val _movie = MutableStateFlow<DataState<MovieDetailsResponse>>(DataState.Idle)
    val movie: MutableStateFlow<DataState<MovieDetailsResponse>> get() = _movie

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            repository.getMovieDetails(movieId)
                .onEach {
                    _movie.value=it
                }.map {
                    if (it is DataState.Success) {
                        val data = it.data
                        it.data.year = MovieConverter.getMovieYear(data.year)
                        it.data.length = MovieConverter.formatMovieLength(data.length)
                        it.data.backdrop = MovieConverter.addBackdropPrefixPath(data.backdrop)
                        it.data.poster = MovieConverter.addPrefixPath(data.poster)
                        it.data.credits?.casts=MovieConverter.extractCastsPictures(data.credits?.casts)
                    }
                }.launchIn(viewModelScope)
        }
    }
}