package com.example.movies.data.model.response

import com.example.movies.data.model.entity.TV_Show

data class TvShowsResponse(
    val results:List<TV_Show>,
    val page:Int,
    val total_pages:Int
)
