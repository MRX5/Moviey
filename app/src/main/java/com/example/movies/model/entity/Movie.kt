package com.example.movies.model.entity

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.SerializedName

data class Movie(
    val id:Int,
    val title:String,
    @SerializedName("poster_path") var poster:String,
    @SerializedName("backdrop_path") var backdrop:String,
    val overview:String,
    @SerializedName("vote_average") var vote:Double?,
    var release_date:String?,
    )
