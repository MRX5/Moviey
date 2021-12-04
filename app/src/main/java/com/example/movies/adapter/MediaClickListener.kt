package com.example.movies.adapter

import com.example.movies.model.entity.Movie

interface MediaClickListener {
    fun onItemClick(mediaType: String, mediaID: Int)
}