package com.example.mvi_template.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TemplateAlbum(
    val userId: Int,
    val id: Int,
    val title: String
)
