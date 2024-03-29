package com.example.movies.data.model.response

import com.example.movies.data.model.entity.Credits
import com.example.movies.data.model.entity.Genres
import com.example.movies.data.model.entity.RelatedMovies
import com.example.movies.data.model.entity.Video
import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    val id:Int,
    var title:String,
    val overview:String,
    @SerializedName("backdrop_path") var backdrop:String?,
    @SerializedName("poster_path") var poster:String?,
    @SerializedName("original_language") val language:String?,
    @SerializedName("release_date") var year:String,
    @SerializedName("vote_average") var vote:Double?,
    @SerializedName("runtime") var length:String?,
    val genres:List<Genres>?,
    val credits: Credits?,
    val videos: Video?,
    val recommendations: RelatedMovies?
)