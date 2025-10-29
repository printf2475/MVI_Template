package com.example.mvi_template.ui.home


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvi_template.domain.usecase.GetTemplateAlbumsUseCase
import com.example.mvi_template.domain.util.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTemplateAlbumsUseCase: GetTemplateAlbumsUseCase
) : ViewModel() {

    private val viewModelState = MutableStateFlow(HomeContract.UiState())

    private val _sideEffect = Channel<HomeContract.SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _uiEvent = Channel<HomeContract.UiEvent>()
    val uiState = _uiEvent.receiveAsFlow()
        .runningFold(viewModelState.value, ::reduceState)
        .stateIn(viewModelScope, SharingStarted.Eagerly, viewModelState.value)

    private fun reduceState(
        current: HomeContract.UiState,
        event: HomeContract.UiEvent
    ): HomeContract.UiState = when (event) {
        is HomeContract.UiEvent.SetIsLoading -> current.copy(isLoading = event.isLoading)
            .also { Log.d(TAG, "SetIsLoading : ${event.isLoading}") }

        is HomeContract.UiEvent.LoadedTemplate -> current.copy(templateAlbums = event.templates)
            .also { Log.d(TAG, "LoadedTemplate : ${event.templates}") }
    }

    init {
        getTemplate()
    }

    private fun getTemplate() = viewModelScope.launch {
        _uiEvent.send(HomeContract.UiEvent.SetIsLoading(true))
        when (val result = getTemplateAlbumsUseCase()) {
            is UseCaseResult.Success -> {
                _uiEvent.send(HomeContract.UiEvent.LoadedTemplate(result.data))
            }

            is UseCaseResult.Error -> {
                _sideEffect.send(HomeContract.SideEffect.TemplateLoadFail(result.exception.message ?: ""))
                Log.d(TAG, "Error : ${result.exception.message}\n ${result.exception.cause}")
            }
        }
        _uiEvent.send(HomeContract.UiEvent.SetIsLoading(false))
    }

    companion object {
        const val TAG: String = "HomeViewModel"
    }
}