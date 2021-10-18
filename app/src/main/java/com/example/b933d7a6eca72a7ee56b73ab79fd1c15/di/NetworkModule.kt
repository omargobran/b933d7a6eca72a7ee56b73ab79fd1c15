package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.di

import android.util.Log
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.network.SpaceStationService
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.network.util.SpaceStationDtoMapper
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.repository.RemoteRepository
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.util.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                    Log.d(Constants.TAG, "Retrofit : $it")
                }).setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .connectTimeout(25, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(45, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideRemoteRepository(
        spaceStationService: SpaceStationService,
        spaceStationDtoMapper: SpaceStationDtoMapper
    ): RemoteRepository {
        return RemoteRepository(spaceStationService, spaceStationDtoMapper)
    }

    @Singleton
    @Provides
    fun provideSpaceStationService(retrofit: Retrofit): SpaceStationService {
        return retrofit.create(SpaceStationService::class.java)
    }

    @Singleton
    @Provides
    fun provideSpaceStationDtoMapper(): SpaceStationDtoMapper {
        return SpaceStationDtoMapper()
    }

}