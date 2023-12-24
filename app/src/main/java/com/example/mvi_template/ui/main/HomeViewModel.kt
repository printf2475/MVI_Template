package com.example.mvi_template.ui.main


import android.util.Log
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

sealed interface HomeUiEvent {
    /**
     * 프로그래스 Visivility 변경
     */
    data class SetIsLoading(
        val isLoading: Boolean
    ) : HomeUiEvent
}

sealed interface HomeUiState {

    /**
     * 프로그래스바 활성화 여부.
     */
    val isLoading: Boolean
}

data class MutableHomeUiState(
    override val isLoading: Boolean = false,
) : HomeUiState

sealed interface HomeSideEffect {
    /**
     * 네트워크 에러
     */
    data class NetworkFail(val exception: Exception) : HomeSideEffect
}

@HiltViewModel
class HomeViewModel @Inject constructor(

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
    }

    init {
        init()
    }

    fun init() = viewModelScope.launch {
        // TODO init
    }
}