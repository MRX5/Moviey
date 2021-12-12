package com.example.movies.model.response

import com.example.movies.model.entity.Media

data class MediaResponse(
    val results:List<Media>,
    val page:Int,
    val total_pages:Int)
