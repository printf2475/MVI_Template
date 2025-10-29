package com.example.mvi_template.data.di

import com.example.mvi_template.data.repository.DefaultRepository
import com.example.mvi_template.data.remote.DefaultRemoteDataSource
import com.example.mvi_template.data.remote.RemoteDataSource
import com.example.mvi_template.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsRemoteDataSource(
        remoteDataSource: DefaultRemoteDataSource
    ): RemoteDataSource

    @Binds
    @Singleton
    fun bindsRepository(
        repository: DefaultRepository
    ): Repository
}