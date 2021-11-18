package com.example.movies.ui.movies.repo

import androidx.lifecycle.LiveData
import com.example.movies.model.Movie
import com.example.movies.utils.Resource

interface MoviesRepository {
    suspend fun getMovies(page:Int): LiveData<Resource<List<Movie>>>
}