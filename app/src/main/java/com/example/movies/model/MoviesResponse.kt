package com.example.movies.model

data class MoviesResponse(
     val results:List<Movie>,
     val page:Int,
     val total_pages:Int
)
