package com.example.mvi_template.domain.usecase

import android.util.Log
import com.example.mvi_template.domain.model.TemplateAlbum
import com.example.mvi_template.domain.repository.Repository
import com.example.mvi_template.domain.util.UseCaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface GetTemplateAlbumsUseCase {
    suspend operator fun invoke(
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    ): UseCaseResult<List<TemplateAlbum>>
}

class GetTemplateAlbumsUseCaseImpl(
    private val repository: Repository
): GetTemplateAlbumsUseCase {
    companion object {
        private const val TAG : String = "GetTemplateAlbumsUseCaseImpl"
    }
    override suspend fun invoke(coroutineDispatcher: CoroutineDispatcher): UseCaseResult<List<TemplateAlbum>> {
        Log.d(TAG, "invoke")
        return kotlin.runCatching {
            withContext(coroutineDispatcher){
                val result = repository.getTemplateAlbums()
                UseCaseResult.Success(result)
            }
        }.getOrElse {
            UseCaseResult.Error(it)
        }
    }
}