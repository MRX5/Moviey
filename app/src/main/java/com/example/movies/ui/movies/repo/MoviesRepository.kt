package com.example.movies.ui.movies.repo

import androidx.lifecycle.LiveData
import com.example.movies.model.response.MoviesResponse
import com.example.movies.utils.Resource

interface MoviesRepository {
    suspend fun getPopularMovies(page:Int): LiveData<Resource<MoviesResponse>>
    suspend fun getUpcomingMovies(page:Int):LiveData<Resource<MoviesResponse>>
    suspend fun getTopRatedMovies(page:Int):LiveData<Resource<MoviesResponse>>
}