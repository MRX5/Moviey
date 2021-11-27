package com.example.movies.model.entity

import com.google.gson.annotations.SerializedName

data class Media(
    val id:Int,
    var title:String,
    @SerializedName("poster_path") var poster:String?,
    val overview:String,
    var release_date:String?,
    var first_air_date:String?,
    val name:String,
    @SerializedName("vote_average") val vote:Double?,
    val genre_ids:List<Int>?,
    val media_type:String
    )
