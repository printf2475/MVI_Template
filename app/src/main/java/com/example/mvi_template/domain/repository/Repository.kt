package com.example.mvi_template.domain.repository

import com.example.mvi_template.domain.model.TemplateAlbum

interface Repository {
    suspend fun getTemplateAlbums(): List<TemplateAlbum>
}