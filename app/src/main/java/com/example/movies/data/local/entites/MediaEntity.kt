package com.example.movies.data.local.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class MediaEntity(
    @PrimaryKey val id:Int,
    val title:String,
    val poster:String?,
    val release_date:String?,
    val vote:Double?,
    val media_type:String
)
