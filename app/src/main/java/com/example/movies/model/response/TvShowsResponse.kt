package com.example.movies.model.response

import com.example.movies.model.entity.Movie
import com.example.movies.model.entity.TV_Show

data class TvShowsResponse(
    val results:List<TV_Show>,
    val page:Int,
    val total_pages:Int
)
