package com.example.hackathon.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
    darkColorScheme(
        primary = Primary,
        secondary = Secondary,
        tertiary = Success,
        background = Secondary,
        surface = SecondaryLight,
        onPrimary = androidx.compose.ui.graphics.Color.White,
        onSecondary = androidx.compose.ui.graphics.Color.White,
        onBackground = androidx.compose.ui.graphics.Color.White,
        onSurface = androidx.compose.ui.graphics.Color.White,
    )

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
fun HackathonTheme(
    darkTheme: Boolean = false,
    // Dynamic color is available on Android 12+
    // 커스텀 색상 사용
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}

object HackathonTheme{
    val colors: HackathonColors
        @Composable
        @ReadOnlyComposable
        get() = LocalHackathonColors

    val typography: HackathonTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalHackathonTypography
}
