package com.example.movies.model.entity

import com.google.gson.annotations.SerializedName

data class Credits(
    @SerializedName("cast") var casts:List<Cast>
)
data class Cast(
    val name:String,
    @SerializedName("profile_path") var profilePicture:String,
    val character:String
)

