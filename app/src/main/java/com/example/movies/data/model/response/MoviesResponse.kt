package com.example.movies.data.model.response

import com.example.movies.data.model.entity.Movie

data class MoviesResponse(
     val results:List<Movie>,
     val page:Int,
     val total_pages:Int
)
