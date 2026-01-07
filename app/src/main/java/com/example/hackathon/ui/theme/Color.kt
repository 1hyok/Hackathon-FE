package com.example.hackathon.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Figma Design System Colors
// Primary Colors
val Primary = Color(0xFFE10818) // 빨간색 (Figma Primary)
val PrimaryDark = Color(0xFFC10714)

// Neutral Colors (Figma Design System)
val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val Gray50 = Color(0xFFE0DCDC)
val Gray700 = Color(0xFF383838)
val Gray900 = Color(0xFF202020)

// Background Colors
val Background = Color(0xFFFFFFFF) // White background
val Surface = Color(0xFFFFFFFF)

// Text Colors
val TextPrimary = Color(0xFF000000) // Black
val TextSecondary = Color(0xFF383838) // Gray700

// Accent Colors
val Success = Color(0xFF2ECC71)
val Error = Color(0xFFE10818) // Primary red for errors

// Legacy colors (for compatibility - deprecated)
val Secondary = Color(0xFF1F2A44)
val SecondaryLight = Color(0xFF2A3A5A)
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)
val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

data class HackathonColors(
    val primary: Color,
    val black: Color,
    val white: Color,
    val gray50: Color,
    val gray700: Color,
    val gray900: Color,
    val background: Color,
    val surface: Color,
    val success: Color,
    val error: Color
)

val defaultHackathonColors = HackathonColors(
    primary = Primary,
    black = Black,
    white = White,
    gray50 = Gray50,
    gray700 = Gray700,
    gray900 = Gray900,
    background = Background,
    surface = Surface,
    success = Success,
    error = Error
)

val LocalHackathonColors: HackathonColors = staticCompositionLocalOf { defaultHackathonColors }
