package com.example.mvi_template.data.mapper

import com.example.mvi_template.data.model.TemplateAlbum
import com.example.mvi_template.domain.model.TemplateAlbum as DomainTemplateAlbum

object TemplateMapper {
    fun toDomainAlbum(album: TemplateAlbum) = DomainTemplateAlbum(
        id = album.id,
        title = album.title
    )
}