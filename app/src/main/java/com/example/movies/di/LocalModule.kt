package com.example.movies.di

import android.content.Context
import androidx.room.Room
import com.example.movies.data.local.AppDatabase
import com.example.movies.data.local.FavouriteDao
import com.example.movies.data.local.source.LocalDataSource
import com.example.movies.data.local.source.LocalDataSourceImpl
import com.example.movies.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideFavouriteDao(database: AppDatabase): FavouriteDao = database.favouriteDao()

    @Singleton
    @Provides
    fun provideLocalDataSource(favouriteDao: FavouriteDao): LocalDataSource =
        LocalDataSourceImpl(favouriteDao)
}