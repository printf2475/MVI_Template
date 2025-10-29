package com.example.mvi_template.ui.home.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mvi_template.ui.home.HomeContract
import com.example.mvi_template.ui.home.component.AlbumListContent


@Composable
fun HomeScreen(
    uiState: HomeContract.UiState
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