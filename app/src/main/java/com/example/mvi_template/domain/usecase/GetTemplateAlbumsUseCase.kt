package com.example.mvi_template.domain.usecase

import com.example.mvi_template.domain.model.TemplateAlbum
import com.example.mvi_template.domain.repository.Repository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTemplateAlbumsUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<List<TemplateAlbum>> = flow {
        println("TAG: $TAG / invoke")
        emit(repository.getTemplateAlbums())
    }

    companion object {
        private const val TAG: String = "GetTemplateAlbumsUseCaseImpl"
    }
}