package com.example.movies.ui.movies.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movies.model.Movie
import com.example.movies.model.MoviesResponse
import com.example.movies.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MoviesRepository {
    suspend fun getPopularMovies(page:Int): LiveData<Resource<MoviesResponse>>
    suspend fun getUpcomingMovies(page:Int):LiveData<Resource<MoviesResponse>>
    suspend fun getTopRatedMovies(page:Int):LiveData<Resource<MoviesResponse>>
}