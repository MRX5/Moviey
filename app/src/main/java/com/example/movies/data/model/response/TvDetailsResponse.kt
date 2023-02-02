package com.example.movies.data.model.response

import com.example.movies.data.model.entity.*
import com.google.gson.annotations.SerializedName

data class TvDetailsResponse(
    val id:Int,
    val overview:String?,
    @SerializedName("name") var title:String,
    @SerializedName("backdrop_path") var backdrop:String?,
    @SerializedName("poster_path") var poster:String?,
    @SerializedName("original_language") val language:String?,
    @SerializedName("first_air_date") var year:String?,
    @SerializedName("vote_average") var vote:Double?,
    val number_of_seasons:Int?,
    val seasons:List<Season>,
    val genres:List<Genres>?,
    val credits: Credits?,
    val videos: Video?,
    val recommendations: RelatedTvShows?
)
