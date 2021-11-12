package com.example.movies.di

import com.example.movies.network.RemoteDataSourceImpl
import com.example.movies.network.ApiService
import com.example.movies.network.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): OkHttpClient {
        val logging=HttpLoggingInterceptor();
        logging.level = HttpLoggingInterceptor.Level.BODY;
        val httpClient =OkHttpClient.Builder();
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiService: ApiService):RemoteDataSource = RemoteDataSourceImpl(apiService)

}