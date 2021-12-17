package com.example.movies.data.model.entity

import com.google.gson.annotations.SerializedName

data class Season(
    val id:Int,
    val name:String?,
    var air_date:String?,
    var episode_count:String?,
    val season_number:Int?,
    @SerializedName("poster_path") var poster:String?
    )
