package com.example.mvi_template.ui.home

import androidx.compose.runtime.Immutable
import com.example.mvi_template.domain.model.TemplateAlbum

object HomeContract {

    sealed interface UiEvent {
        /**
         * 프로그래스 Visivility 변경
         */
        data class SetIsLoading(
            val isLoading: Boolean
        ) : UiEvent

        /**
         * 템플릿 데이터 로드
         */
        data class LoadedTemplate(
            val templates: List<TemplateAlbum>
        ) : UiEvent
    }

    @Immutable
    data class UiState(
        val isLoading: Boolean = false,
        val templateAlbums: List<TemplateAlbum> = emptyList(),
    )

    sealed interface SideEffect {
        /**
         * 템플릿 로드 실패
         */
        data class TemplateLoadFail(val message: String) : SideEffect
    }
}