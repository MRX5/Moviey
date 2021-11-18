package com.example.movies.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.SerializedName

data class Movie(
    val link:String?,
    val imageUrl:String?,
    val title:String?,
    val year:String?
)