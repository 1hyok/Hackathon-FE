package com.example.hackathon.presentation.screen.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.hackathon.ui.theme.HackathonTheme

@Composable
fun Convenient(modifier: Modifier = Modifier)  {
    LazyColumn(
        modifier =
            Modifier.fillMaxSize()
                .background(Color(0xFFF6F3F1)),
    ) {
    }
}
