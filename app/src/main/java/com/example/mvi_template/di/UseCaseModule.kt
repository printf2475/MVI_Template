package com.example.mvi_template.di

import com.example.mvi_template.domain.repository.Repository
import com.example.mvi_template.domain.usecase.GetTemplateAlbumsUseCase
import com.example.mvi_template.domain.usecase.GetTemplateAlbumsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideGetTemplateUseCase(
        repository: Repository
    ): GetTemplateAlbumsUseCase = GetTemplateAlbumsUseCaseImpl(
        repository = repository
    )
}