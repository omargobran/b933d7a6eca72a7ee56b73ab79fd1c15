package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.di

import android.content.Context
import androidx.room.Room
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.SpaceDatabase
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.dao.SpaceStationDao
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.dao.SpaceshipDao
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.util.SpaceStationEntityMapper
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.cache.util.SpaceshipEntityMapper
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.Spaceship
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.repository.LocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideVideoGameDb(@ApplicationContext context: Context): SpaceDatabase {
        return Room.databaseBuilder(
            context,
            SpaceDatabase::class.java,
            SpaceDatabase.DB_NAME
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideLocalRepository(
        spaceStationDao: SpaceStationDao,
        spaceshipDao: SpaceshipDao,
        spaceStationEntityMapper: SpaceStationEntityMapper,
        spaceshipEntityMapper: SpaceshipEntityMapper
    ): LocalRepository {
        return LocalRepository(
            spaceStationDao,
            spaceshipDao,
            spaceStationEntityMapper,
            spaceshipEntityMapper
        )
    }

    @Singleton
    @Provides
    fun provideSpaceStationDao(spaceDatabase: SpaceDatabase): SpaceStationDao {
        return spaceDatabase.spaceStationDao()
    }

    @Singleton
    @Provides
    fun provideSpaceshipDao(spaceDatabase: SpaceDatabase): SpaceshipDao {
        return spaceDatabase.spaceshipDao()
    }

    @Singleton
    @Provides
    fun provideSpaceStationEntityMapper(): SpaceStationEntityMapper {
        return SpaceStationEntityMapper()
    }

    @Singleton
    @Provides
    fun provideSpaceshipEntityMapper(): SpaceshipEntityMapper {
        return SpaceshipEntityMapper()
    }

}