package com.example.hackathon.core.designsystem.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

/**
 * 앱 전역 아이콘 단일 소스.
 *
 * 수천 개의 미사용 아이콘으로 APK 를 키우는 material-icons-extended 를 제거하고,
 * 실제 사용하는 아이콘만 이 객체로 모았다. feature/app 모듈은 material-icons 에
 * 직접 의존하지 않고 이 객체만 참조한다.
 *
 * - core 세트(material-icons-core)에 포함된 아이콘은 그대로 위임한다.
 * - ArrowBack 은 RTL 자동 미러링되는 AutoMirrored 변형으로 통일(deprecated Filled.ArrowBack 회피).
 * - core 에 없는 Visibility / VisibilityOff 만 원본(material-icons 1.7.8) path 를
 *   public ImageVector DSL 로 재현해 직접 보유한다.
 */
object HackathonIcons {
    val Add: ImageVector get() = Icons.Default.Add
    val Search: ImageVector get() = Icons.Default.Search
    val Close: ImageVector get() = Icons.Default.Close
    val Favorite: ImageVector get() = Icons.Default.Favorite
    val FavoriteBorder: ImageVector get() = Icons.Outlined.FavoriteBorder
    val ArrowBack: ImageVector get() = Icons.AutoMirrored.Filled.ArrowBack
    val Visibility: ImageVector get() = visibilityIcon
    val VisibilityOff: ImageVector get() = visibilityOffIcon
}

// ── 커스텀 ImageVector 2종 (material-icons-core 에 없는 Visibility/VisibilityOff) ──
// extended 1.7.8 의 Filled.Visibility/VisibilityOff path 를 그대로 옮겨 렌더 결과는 동일하다.
// (extended 의 materialIcon{}/materialPath{} 는 internal 이라 동등한 public API 로 재현)
//
// 읽는 법 — SVG 의 <path d="..."> 를 함수 호출로 1:1 옮긴 것이다:
//  - viewportWidth/Height(24) = 좌표 격자. 아래 숫자들은 이 24x24 격자 안의 좌표다.
//    defaultWidth/Height(24.dp) = 기본 표시 크기(실제론 Icon 의 Modifier.size 가 덮음).
//  - path(fill = Black) = 도형을 채움. 화면 색은 Icon(tint=...) 이 덮으므로 검정은 형식상 기본값.
//  - moveTo(x,y)      = 펜을 (x,y) 로 이동(선 없이). 새 닫힌 도형(subpath) 시작.
//  - lineTo / curveTo = 직선 / 3차 베지어 곡선 (대문자 = 절대 좌표).
//  - ...Relative 접미사 = 현재 위치 기준 상대 좌표(dx,dy) (SVG 소문자 명령).
//  - reflectiveCurve  = 직전 곡선 제어점을 대칭 재사용해 매끄럽게 잇는 곡선 (SVG 의 s).
//  - close()          = 시작점으로 직선을 그어 도형을 닫음.
private val visibilityIcon: ImageVector =
    ImageVector.Builder(
        name = "HackathonIcons.Visibility",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        // 닫힌 도형 3개로 눈 모양을 그린다 (moveTo~close 가 3쌍).
        path(fill = SolidColor(Color.Black)) {
            // 도형 1: 바깥 눈 윤곽
            moveTo(12.0f, 4.5f)
            curveTo(7.0f, 4.5f, 2.73f, 7.61f, 1.0f, 12.0f)
            curveToRelative(1.73f, 4.39f, 6.0f, 7.5f, 11.0f, 7.5f)
            reflectiveCurveToRelative(9.27f, -3.11f, 11.0f, -7.5f)
            curveToRelative(-1.73f, -4.39f, -6.0f, -7.5f, -11.0f, -7.5f)
            close()
            // 도형 2: 가운데 원(홍채)
            moveTo(12.0f, 17.0f)
            curveToRelative(-2.76f, 0.0f, -5.0f, -2.24f, -5.0f, -5.0f)
            reflectiveCurveToRelative(2.24f, -5.0f, 5.0f, -5.0f)
            reflectiveCurveToRelative(5.0f, 2.24f, 5.0f, 5.0f)
            reflectiveCurveToRelative(-2.24f, 5.0f, -5.0f, 5.0f)
            close()
            // 도형 3: 안쪽 원(동공)
            moveTo(12.0f, 9.0f)
            curveToRelative(-1.66f, 0.0f, -3.0f, 1.34f, -3.0f, 3.0f)
            reflectiveCurveToRelative(1.34f, 3.0f, 3.0f, 3.0f)
            reflectiveCurveToRelative(3.0f, -1.34f, 3.0f, -3.0f)
            reflectiveCurveToRelative(-1.34f, -3.0f, -3.0f, -3.0f)
            close()
        }
    }.build()

// VisibilityOff = 위 Visibility 와 같은 방식. 눈 + 대각선 슬래시까지 닫힌 도형 4개로 구성.
private val visibilityOffIcon: ImageVector =
    ImageVector.Builder(
        name = "HackathonIcons.VisibilityOff",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(12.0f, 7.0f)
            curveToRelative(2.76f, 0.0f, 5.0f, 2.24f, 5.0f, 5.0f)
            curveToRelative(0.0f, 0.65f, -0.13f, 1.26f, -0.36f, 1.83f)
            lineToRelative(2.92f, 2.92f)
            curveToRelative(1.51f, -1.26f, 2.7f, -2.89f, 3.43f, -4.75f)
            curveToRelative(-1.73f, -4.39f, -6.0f, -7.5f, -11.0f, -7.5f)
            curveToRelative(-1.4f, 0.0f, -2.74f, 0.25f, -3.98f, 0.7f)
            lineToRelative(2.16f, 2.16f)
            curveTo(10.74f, 7.13f, 11.35f, 7.0f, 12.0f, 7.0f)
            close()
            moveTo(2.0f, 4.27f)
            lineToRelative(2.28f, 2.28f)
            lineToRelative(0.46f, 0.46f)
            curveTo(3.08f, 8.3f, 1.78f, 10.02f, 1.0f, 12.0f)
            curveToRelative(1.73f, 4.39f, 6.0f, 7.5f, 11.0f, 7.5f)
            curveToRelative(1.55f, 0.0f, 3.03f, -0.3f, 4.38f, -0.84f)
            lineToRelative(0.42f, 0.42f)
            lineTo(19.73f, 22.0f)
            lineTo(21.0f, 20.73f)
            lineTo(3.27f, 3.0f)
            lineTo(2.0f, 4.27f)
            close()
            moveTo(7.53f, 9.8f)
            lineToRelative(1.55f, 1.55f)
            curveToRelative(-0.05f, 0.21f, -0.08f, 0.43f, -0.08f, 0.65f)
            curveToRelative(0.0f, 1.66f, 1.34f, 3.0f, 3.0f, 3.0f)
            curveToRelative(0.22f, 0.0f, 0.44f, -0.03f, 0.65f, -0.08f)
            lineToRelative(1.55f, 1.55f)
            curveToRelative(-0.67f, 0.33f, -1.41f, 0.53f, -2.2f, 0.53f)
            curveToRelative(-2.76f, 0.0f, -5.0f, -2.24f, -5.0f, -5.0f)
            curveToRelative(0.0f, -0.79f, 0.2f, -1.53f, 0.53f, -2.2f)
            close()
            moveTo(11.84f, 9.02f)
            lineToRelative(3.15f, 3.15f)
            lineToRelative(0.02f, -0.16f)
            curveToRelative(0.0f, -1.66f, -1.34f, -3.0f, -3.0f, -3.0f)
            lineToRelative(-0.17f, 0.01f)
            close()
        }
    }.build()
