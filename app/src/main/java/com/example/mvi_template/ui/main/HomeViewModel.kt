package com.example.mvi_template.ui.main


import android.util.Log
import androidx.compose.runtime.Immutable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvi_template.domain.model.TemplateAlbum
import com.example.mvi_template.domain.usecase.GetTemplateAlbumsUseCase
import com.example.mvi_template.domain.util.UseCaseResult

sealed interface HomeUiEvent {
    /**
     * 프로그래스 Visivility 변경
     */
    data class SetIsLoading(
        val isLoading: Boolean
    ) : HomeUiEvent

    /**
     * 템플릿 데이터 로드
     */
    data class LoadedTemplate(
        val templates: List<TemplateAlbum>
    ) : HomeUiEvent
}

@Immutable
sealed interface HomeUiState {

    /**
     * 프로그래스바 활성화 여부.
     */
    val isLoading: Boolean

    /**
     * 템플릿 데이터
     */
    val templateAlbums: List<TemplateAlbum>
}

@Immutable
data class MutableHomeUiState(
    override val isLoading: Boolean = false,
    override val templateAlbums: List<TemplateAlbum> = emptyList(),
) : HomeUiState

sealed interface HomeSideEffect {
    /**
     * 템플릿 로드 실패
     */
    data class TemplateLoadFail(val message: String) : HomeSideEffect
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTemplateAlbumsUseCase: GetTemplateAlbumsUseCase
) : ViewModel() {

    companion object {
        const val TAG: String = "HomeViewModel"
    }

    private val viewModelState = MutableStateFlow(
        MutableHomeUiState()
    )

    /**
     * ViewModel 메소드에서 호출.
     */
    private val _sideEffect = Channel<HomeSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _uiEvent = Channel<HomeUiEvent>()
    val uiState = _uiEvent.receiveAsFlow()
        .runningFold(viewModelState.value, ::reduceState)
        .stateIn(viewModelScope, SharingStarted.Eagerly, viewModelState.value)

    private fun reduceState(
        current: MutableHomeUiState,
        event: HomeUiEvent
    ): MutableHomeUiState = when (event) {
        is HomeUiEvent.SetIsLoading -> current.copy(isLoading = event.isLoading)
            .also { Log.d(TAG, "SetIsLoading : ${event.isLoading}") }

        is HomeUiEvent.LoadedTemplate -> current.copy(templateAlbums = event.templates)
            .also { Log.d(TAG, "LoadedTemplate : ${event.templates}") }
    }

    init {
        getTemplate()
    }

    private fun getTemplate() = viewModelScope.launch {
        _uiEvent.send(HomeUiEvent.SetIsLoading(true))
        when (val result = getTemplateAlbumsUseCase()) {
            is UseCaseResult.Success -> {
                _uiEvent.send(HomeUiEvent.LoadedTemplate(result.data))
            }

            is UseCaseResult.Error -> {
                _sideEffect.send(HomeSideEffect.TemplateLoadFail(result.exception.message?:""))
                Log.d(TAG, "Error : ${result.exception.message }\n ${result.exception.cause}")
            }
        }
        _uiEvent.send(HomeUiEvent.SetIsLoading(false))
    }
}