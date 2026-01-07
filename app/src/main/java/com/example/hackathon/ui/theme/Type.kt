package com.example.hackathon.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Figma Design System Typography
// TODO: Pretendard 폰트 파일 추가 후 FontFamily 적용
// 예시: FontFamily(Font(R.font.pretendard_bold, FontWeight.Bold))
// 현재는 시스템 기본 폰트 사용
val Typography =
    Typography(
        // Head1_bold: 24px, Bold, lineHeight 28px
        displaySmall =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.24.sp,
            ),
        // Head1_semibold: 24px, SemiBold, lineHeight 28px
        // Head2_bold: 22px, Bold, lineHeight 24px
        titleLarge =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                lineHeight = 24.sp,
            ),
        // Head2_semibold: 22px, SemiBold, lineHeight 24px
        // Sub1_semibold: 18px, SemiBold, lineHeight 20px
        titleMedium =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                lineHeight = 20.sp,
            ),
        // Sub1_medium: 18px, Medium, lineHeight 20px
        // Sub2_semibold: 16px, SemiBold, lineHeight 18px
        titleSmall =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                lineHeight = 18.sp,
            ),
        // Sub2_medium: 16px, Medium, lineHeight 18px
        bodyLarge =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                lineHeight = 18.sp,
            ),
        // Body_semibold: 14px, SemiBold, lineHeight 16px
        bodyMedium =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                lineHeight = 16.sp,
            ),
        // Body_medium: 14px, Medium, lineHeight 16px
        labelLarge =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                lineHeight = 16.sp,
            ),
        // Caption_medium: 12px, Medium, lineHeight 14px
        labelMedium =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 14.sp,
            ),
        labelSmall =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                lineHeight = 14.sp,
            ),
        // Material3 기본 스타일 (사용되지 않을 수 있음)
        displayLarge =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                lineHeight = 40.sp,
            ),
        displayMedium =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                lineHeight = 36.sp,
            ),
        bodySmall =
            TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                lineHeight = 16.sp,
            ),
    )

// Figma Design System 커스텀 타이포그래피 스타일
// Material3 Typography에 없는 스타일들을 명시적으로 정의
object CustomTypography {
    // Head1_semibold: 24px, SemiBold, lineHeight 28px
    val Head1Semibold =
        TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 28.sp,
        )

    // Head2_semibold: 22px, SemiBold, lineHeight 24px
    val Head2Semibold =
        TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            lineHeight = 24.sp,
        )

    // Sub1_medium: 18px, Medium, lineHeight 20px
    val Sub1Medium =
        TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            lineHeight = 20.sp,
        )

    // Sub2_semibold: 16px, SemiBold, lineHeight 18px
    val Sub2Semibold =
        TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 18.sp,
        )
}
