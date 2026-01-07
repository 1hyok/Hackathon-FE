package com.example.hackathon.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

// 다크 모드 미지원 - 항상 라이트 모드만 사용
private val LightColorScheme =
    lightColorScheme(
        primary = Primary,
        secondary = Gray900,
        tertiary = Success,
        // Figma: White background
        background = White,
        surface = White,
        onPrimary = White,
        onSecondary = White,
        // Figma: Black text
        onBackground = Black,
        onSurface = Black,
        error = Error,
        onError = White,
    )

@Composable
fun HackathonTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content,
    )
}

object HackathonTheme {
    val colors: HackathonColors
        @Composable
        @ReadOnlyComposable
        get() = LocalHackathonColors.current

    val typography: HackathonTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalHackathonTypography.current
}
