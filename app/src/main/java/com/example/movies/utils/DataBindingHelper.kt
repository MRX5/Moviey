package com.example.movies.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("loadImage")
fun loadMovieImage(imageView: ImageView, url: String?) {
    url?.let {
        Picasso.get().load(Constants.TMDB_IMAGE_PATH + it).into(imageView)
    }
}

@BindingAdapter("loadBackdropImage")
fun loadMovieBackdropImage(imageView: ImageView, url: String?) {
    url?.let {
        Picasso.get().load(Constants.TMDB_BACKDROP_PATH + it).into(imageView)
    }
}