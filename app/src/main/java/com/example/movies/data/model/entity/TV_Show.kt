package com.example.movies.data.model.entity

import com.google.gson.annotations.SerializedName

data class TV_Show(
    val id: Int,
    val name: String,
    @SerializedName("poster_path") var poster: String?,
    @SerializedName("backdrop_path") var backdrop:String,
    val overview: String?,
    @SerializedName("vote_average") var vote:Double?,
    @SerializedName("first_air_date") var release_date: String?,
)