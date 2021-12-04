package com.example.movies.model.entity

import com.google.gson.annotations.SerializedName

data class TV_Show(
    val id: Int,
    val name: String,
    @SerializedName("poster_path") var poster: String?,
    val overview: String?,
    @SerializedName("first_air_date") var release_date: String?,
)