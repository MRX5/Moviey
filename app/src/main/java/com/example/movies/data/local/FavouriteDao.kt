package com.example.movies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movies.data.local.entites.MediaEntity
import com.example.movies.utils.DataState
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavourites(entity: MediaEntity)

    @Query("select * from favourites where id=:mediaId")
    suspend fun isFavourite(mediaId:Int):MediaEntity

    @Query("delete from favourites where id=:mediaId")
    suspend fun removeFromFavourites(mediaId: Int)

    @Query("select * from favourites")
    suspend fun getFavourites(): List<MediaEntity>
}