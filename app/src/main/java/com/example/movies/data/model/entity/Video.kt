package com.example.movies.data.model.entity

data class Video(
     val results:List<Trailer>
)
data class Trailer(
    val name:String,
    val key:String,
    val type:String
)