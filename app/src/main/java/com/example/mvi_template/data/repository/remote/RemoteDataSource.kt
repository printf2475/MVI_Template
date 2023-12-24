package com.example.mvi_template.data.repository.remote

import com.example.mvi_template.data.api.TemplateApi
import com.example.mvi_template.data.model.TemplateAlbum

interface RemoteDataSource {
    suspend fun getTemplateAlbums() : List<TemplateAlbum>
}

class RemoteDataSourceImpl(
    private val templateApi: TemplateApi
) : RemoteDataSource {
    override suspend fun getTemplateAlbums(): List<TemplateAlbum> = templateApi.getTemplateAlbums()
}