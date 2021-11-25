package com.example.movies.ui.movies.adapter

import com.example.movies.model.Movie

interface MovieClickListener {
    fun onMovieClick(movie: Movie)
}