package com.example.movies.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.SerializedName

data class Movie(
    val id:Int,
    val title:String,
    @SerializedName("poster_path")
    var poster:String,
    val overview:String,
    var release_date:String,
    @SerializedName("vote_average")
    val vote:Double,
    @SerializedName("backdrop_path")
    val backdrop:String,
    @SerializedName("original_language")
    val language:String
){
/*    companion object{
    @BindingAdapter("android:imageUrl")
    fun loadImage(view: ImageView, poster:String?){
        if(poster!=null)Picasso.get().load(poster).into(view)
    }}*/
}
