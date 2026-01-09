package com.example.hackathon.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hackathon.core.component.TopAppLogoBar
import com.example.hackathon.ui.theme.Primary
import kotlinx.coroutines.delay

// 담당자: 일혁
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
) {
    // 2초 후 자동으로 홈 화면으로 이동 (또는 로그인 상태에 따라)
    LaunchedEffect(Unit) {
        delay(2000)
        // TODO: 로그인 상태 확인 후 분기
        // 현재는 홈 화면으로 이동
        onNavigateToHome()
    }

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(24.dp),
        ) {
            // 로고 영역
            TopAppLogoBar()

            // 시작하기 버튼
            Button(
                onClick = onNavigateToHome,
                modifier = Modifier.fillMaxWidth(0.8f),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(15.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = Color.White,
                    ),
            ) {
                Text(
                    text = "시작하기",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}
