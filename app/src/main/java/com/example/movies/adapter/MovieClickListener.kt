package com.example.movies.adapter

import com.example.movies.model.entity.Movie

interface MovieClickListener {
    fun onMovieClick(movie: Movie)
}