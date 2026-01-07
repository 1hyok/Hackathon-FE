package com.example.hackathon.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.hackathon.R
import androidx.compose.ui.text.font.Font

// 담당자: 일혁
// TODO: Pretendard 폰트 리소스를 추가한 뒤 FontFamily를 교체해야 함.
// 현재는 시스템 기본 폰트(FontFamily.Default)를 사용.

val PretendardFontFamily =
    FontFamily(
        Font(R.font.pretendard_regular, FontWeight.Normal),
        Font(R.font.pretendard_medium, FontWeight.Medium),
        Font(R.font.pretendard_semibold, FontWeight.SemiBold),
        Font(R.font.pretendard_bold, FontWeight.Bold),
    )

// Figma 명칭과 1:1로 매핑된 스타일 세트
object FigmaTypography {
    val Head1Bold =
        TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 28.sp,
        )
    val Head1Semibold =
        TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 28.sp,
        )
    val Head2Bold =
        TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 24.sp,
        )
    val Head2Semibold =
        TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            lineHeight = 24.sp,
        )
    val Sub1Semibold =
        TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = 20.sp,
        )
    val Sub1Medium =
        TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            lineHeight = 20.sp,
        )
    val Sub2Semibold =
        TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 18.sp,
        )
    val Sub2Medium =
        TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 18.sp,
        )
    val BodySemibold =
        TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 16.sp,
        )
    val BodyMedium =
        TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 16.sp,
        )
    val CaptionMedium =
        TextStyle(
            fontFamily = PretendardFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 14.sp,
        )
}

// Material3 Typography에 기본 맵핑 (가장 가까운 Figma 이름으로 대응)
val Typography =
    Typography(
        displaySmall = FigmaTypography.Head1Bold, // 24 Bold
        titleLarge = FigmaTypography.Head2Bold, // 22 Bold
        titleMedium = FigmaTypography.Sub1Semibold, // 18 SemiBold
        titleSmall = FigmaTypography.Sub2Semibold, // 16 SemiBold
        bodyLarge = FigmaTypography.Sub2Medium, // 16 Medium
        bodyMedium = FigmaTypography.BodySemibold, // 14 SemiBold
        labelLarge = FigmaTypography.BodyMedium, // 14 Medium
        labelMedium = FigmaTypography.CaptionMedium, // 12 Medium
    )
