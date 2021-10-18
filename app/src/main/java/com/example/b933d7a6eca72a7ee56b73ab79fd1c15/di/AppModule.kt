package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.di

import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.repository.LocalRepository
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.repository.RemoteRepository
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRepository(
        localRepository: LocalRepository,
        remoteRepository: RemoteRepository
    ): Repository {
        return Repository(localRepository, remoteRepository)
    }

}