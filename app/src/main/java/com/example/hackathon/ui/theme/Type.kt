package com.example.hackathon.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.hackathon.R

// Figma Design System Typography
// TODO: Pretendard 폰트 파일 추가 후 FontFamily 적용
// 예시: FontFamily(Font(R.font.pretendard_bold, FontWeight.Bold))
// 현재는 시스템 기본 폰트 사용

val FontBold = FontFamily(Font(R.font.pretendard_bold))
val FontSemiBold = FontFamily(Font(R.font.pretendard_semibold))
val FontMedium = FontFamily(Font(R.font.pretendard_medium))

data class HackathonTypography(
    val Head1_bold: TextStyle,
    val Head1_semibold: TextStyle,
    val Head2_bold: TextStyle,
    val Head2_semibold: TextStyle,
    val Sub1_semibold: TextStyle,
    val Sub1_medium: TextStyle,
    val Sub2_semibold: TextStyle,
    val Sub2_medium: TextStyle,
    val Body_semibold: TextStyle,
    val Body_medium: TextStyle,
    val Caption_medium: TextStyle,
)

val defaultHackathonTypography =
    HackathonTypography(
        Head1_bold =
            TextStyle(
                fontFamily = FontBold,
                fontSize = 24.sp,
                lineHeight = 28.sp,
            ),
        Head1_semibold =
            TextStyle(
                fontFamily = FontSemiBold,
                fontSize = 24.sp,
                lineHeight = 28.sp,
            ),
        Head2_bold =
            TextStyle(
                fontFamily = FontBold,
                fontSize = 22.sp,
                lineHeight = 24.sp,
            ),
        Head2_semibold =
            TextStyle(
                fontFamily = FontSemiBold,
                fontSize = 22.sp,
                lineHeight = 24.sp,
            ),
        Sub1_semibold =
            TextStyle(
                fontFamily = FontSemiBold,
                fontSize = 18.sp,
                lineHeight = 20.sp,
            ),
        Sub1_medium =
            TextStyle(
                fontFamily = FontMedium,
                fontSize = 18.sp,
                lineHeight = 20.sp,
            ),
        Sub2_semibold =
            TextStyle(
                fontFamily = FontSemiBold,
                fontSize = 24.sp,
                lineHeight = 28.sp,
            ),
        Sub2_medium =
            TextStyle(
                fontFamily = FontBold,
                fontSize = 16.sp,
                lineHeight = 18.sp,
            ),
        Body_semibold =
            TextStyle(
                fontFamily = FontSemiBold,
                fontSize = 14.sp,
                lineHeight = 16.sp,
            ),
        Body_medium =
            TextStyle(
                fontFamily = FontMedium,
                fontSize = 14.sp,
                lineHeight = 16.sp,
            ),
        Caption_medium =
            TextStyle(
                fontFamily = FontMedium,
                fontSize = 12.sp,
                lineHeight = 14.sp,
            ),
    )

val LocalHackathonTypography = staticCompositionLocalOf { defaultHackathonTypography }

val Typography =
    Typography(
        bodyLarge =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp,
            ),
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
     */
    )
