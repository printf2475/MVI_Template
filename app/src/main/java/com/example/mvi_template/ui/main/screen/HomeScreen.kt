package com.example.mvi_template.ui.main.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mvi_template.ui.main.HomeSideEffect
import com.example.mvi_template.ui.main.HomeUiState
import com.example.mvi_template.ui.main.HomeViewModel
import com.example.mvi_template.ui.main.contents.AlbumListContent

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    val context: Context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is HomeSideEffect.TemplateLoadFail -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    HomeScreen(uiState = uiState)
}

@Composable
private fun HomeScreen(
    uiState: HomeUiState
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            AlbumListContent(
                albumList = uiState.templateAlbums
            )


            AnimatedVisibility(
                modifier = Modifier.align(Alignment.Center),
                visible = uiState.isLoading
            ) {
                CircularProgressIndicator()
            }
        }
    }
}