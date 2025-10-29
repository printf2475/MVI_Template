package com.example.mvi_template.data.repository.remote

import com.example.mvi_template.data.api.TemplateApi
import com.example.mvi_template.data.model.TemplateAlbum
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun getTemplateAlbums() : List<TemplateAlbum>
}

class DefaultRemoteDataSource @Inject constructor(
    private val templateApi: TemplateApi
) : RemoteDataSource {
    override suspend fun getTemplateAlbums(): List<TemplateAlbum> = templateApi.getTemplateAlbums()
}