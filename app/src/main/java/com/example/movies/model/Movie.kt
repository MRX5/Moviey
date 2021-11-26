package com.example.movies.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.SerializedName

data class Movie(
    val id:Int,
    val title:String,
    @SerializedName("poster_path") var poster:String,
    val overview:String,
    var release_date:String, )
