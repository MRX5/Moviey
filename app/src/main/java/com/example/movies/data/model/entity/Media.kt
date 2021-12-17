package com.example.movies.data.model.entity

import com.google.gson.annotations.SerializedName

data class Media(
    val id:Int,
    var title:String,
    @SerializedName("poster_path") var poster:String?,
    @SerializedName("backdrop_path") var backdrop:String?,
    val overview:String,
    var release_date:String?,
    var first_air_date:String?,
    val name:String,
    @SerializedName("vote_average") val vote:Double?,
    val genre_ids:List<Int>?,
    val media_type:String
    )
