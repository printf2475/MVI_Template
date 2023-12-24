package com.example.mvi_template.di

import com.example.mvi_template.data.api.TemplateApi
import com.example.mvi_template.data.repository.RepositoryImpl
import com.example.mvi_template.data.repository.remote.RemoteDataSourceImpl
import com.example.mvi_template.domain.repository.Repository
import com.example.mvi_template.data.repository.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRemoteDataSource(
        templateApi: TemplateApi
    ): RemoteDataSource = RemoteDataSourceImpl(
        templateApi = templateApi
    )

    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: RemoteDataSource
    ): Repository = RepositoryImpl(
        remoteDataSource = remoteDataSource
    )
}