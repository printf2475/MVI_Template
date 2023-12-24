package com.example.mvi_template.data.repository

import com.example.mvi_template.data.mapper.TemplateMapper
import com.example.mvi_template.domain.model.TemplateAlbum
import com.example.mvi_template.domain.repository.Repository
import com.example.mvi_template.data.repository.remote.RemoteDataSource

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : Repository {
    override suspend fun getTemplateAlbums(): List<TemplateAlbum> =
        remoteDataSource.getTemplateAlbums().map { TemplateMapper.toDomainAlbum(it) }
}