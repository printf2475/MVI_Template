package com.example.mvi_template.di

import com.example.mvi_template.data.api.TemplateApi
import com.example.mvi_template.data.api.TemplateApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideTemplateApi(
        client: HttpClient
    ): TemplateApi = TemplateApiImpl(
        client = client
    )
}