package com.example.mvi_template.data.api

import com.example.mvi_template.data.model.TemplateAlbum
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


interface TemplateApi {
    suspend fun getTemplateAlbums() : List<TemplateAlbum>
}

class TemplateApiImpl(
    private val client: HttpClient
): TemplateApi{
    override suspend fun getTemplateAlbums(): List<TemplateAlbum> {
        return client.get("albums").body()
    }

}