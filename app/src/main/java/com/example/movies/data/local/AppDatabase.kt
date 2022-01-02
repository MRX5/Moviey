package com.example.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movies.data.local.entites.MediaEntity

@Database(entities = [MediaEntity::class],version = 1,exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favouriteDao():FavouriteDao
}