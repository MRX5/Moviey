package com.example.movies.ui.tv_details.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.model.response.MovieDetailsResponse
import com.example.movies.model.response.TvDetailsResponse
import com.example.movies.ui.tv_details.repo.TvDetailsRepository
import com.example.movies.utils.DataState
import com.example.movies.utils.MediaUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvDetailsViewModel @Inject constructor(private val repository:TvDetailsRepository) : ViewModel() {
    private val _tvShow = MutableStateFlow<DataState<TvDetailsResponse>>(DataState.Idle)
    val tvShow: MutableStateFlow<DataState<TvDetailsResponse>> get() = _tvShow

    fun getMovieDetails(tvID: Int) {
        viewModelScope.launch {
            repository.getTvShowDetails(tvID)
                .onEach {
                    _tvShow.value = it
                }.map {
                    if (it is DataState.Success) {
                        val data = it.data
                        it.data.year = MediaUtils.extractYearFromDate(data.year)
                        it.data.backdrop = MediaUtils.addBackdropPrefixPath(data.backdrop)
                        it.data.poster = MediaUtils.addPrefixPath(data.poster)
                        it.data.credits?.casts = MediaUtils.extractCastsPictures(data.credits?.casts)
                    }
                }.launchIn(viewModelScope)
        }
    }
}