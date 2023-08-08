package com.homework.mymoviebox.di


import com.homework.mymoviebox.data.RepositoryImpl
import com.homework.mymoviebox.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(repositoryImpl: RepositoryImpl): Repository
}