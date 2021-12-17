package com.example.movies.data.model.response

import com.example.movies.data.model.entity.Media

data class MediaResponse(
    val results:List<Media>,
    val page:Int,
    val total_pages:Int)
