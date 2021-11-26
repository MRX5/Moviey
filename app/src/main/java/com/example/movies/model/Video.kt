package com.example.movies.model

import com.google.gson.annotations.SerializedName

data class Video(
     val results:List<Trailer>
)
data class Trailer(
    val name:String,
    val key:String,
    val type:String
)