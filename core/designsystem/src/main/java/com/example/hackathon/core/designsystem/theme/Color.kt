package com.example.hackathon.core.designsystem.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Figma Design System Colors
// Primary Colors
val Primary = Color(0xFFE10818) // 빨간색 (Figma Primary)

// Neutral Colors (Figma Design System)
val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val Gray50 = Color(0xFFE0DCDC)
val Gray700 = Color(0xFF383838)
val Gray900 = Color(0xFF202020)

// 추가 그레이/표면 토큰 — 화면에 하드코딩돼 있던 Color(0xFF...) 정리 (#31)
val Gray200 = Color(0xFFCCCCCC)
val Gray300 = Color(0xFFC0C0C0)
val Gray400 = Color(0xFF939DA9)
val Gray500 = Color(0xFF8B91A1)
val Gray600 = Color(0xFF555555)
val SurfaceVariant = Color(0xFFF7F7F7)
val PrimaryContainer = Color(0xFFFFF3F3)

// Background Colors
val Background = Color(0xFFFFFFFF) // White background
val Surface = Color(0xFFFFFFFF)

// Accent Colors
val Success = Color(0xFF2ECC71)
val Error = Color(0xFFE10818) // Primary red for errors

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
    val error: Color,
    // #31 — 화면 하드코딩 색을 역할 기반 토큰으로 추가 (다크모드 도입 대비, 라이트/다크 공통 역할)
    val iconUnselected: Color,
    val labelUnselected: Color,
    val onSurfaceVariant: Color,
    val outline: Color,
    val textSecondary: Color,
    val surfaceVariant: Color,
    val primaryContainer: Color
)

val defaultHackathonColors =
    HackathonColors(
        primary = Primary,
        black = Black,
        white = White,
        gray50 = Gray50,
        gray700 = Gray700,
        gray900 = Gray900,
        background = Background,
        surface = Surface,
        success = Success,
        error = Error,
        iconUnselected = Gray500,
        labelUnselected = Gray400,
        onSurfaceVariant = Gray300,
        outline = Gray200,
        textSecondary = Gray600,
        surfaceVariant = SurfaceVariant,
        primaryContainer = PrimaryContainer
    )

val LocalHackathonColors = staticCompositionLocalOf { defaultHackathonColors }
