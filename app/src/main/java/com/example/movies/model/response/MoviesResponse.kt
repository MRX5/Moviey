package com.example.movies.model.response

import com.example.movies.model.entity.Movie

data class MoviesResponse(
     val results:List<Movie>,
     val page:Int,
     val total_pages:Int
)
