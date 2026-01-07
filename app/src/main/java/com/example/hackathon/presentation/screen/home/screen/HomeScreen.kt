package com.example.hackathon.presentation.screen.home.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hackathon.core.component.TopAppLogoBar
import com.example.hackathon.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onCombinationClick: (String) -> Unit = {},
    onCreateClick: () -> Unit = {},
) {
    // 최소 UI 유지: 앱이 크래시 되지 않도록 안전한 플레이스홀더만 표시
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TopAppLogoBar()
}
