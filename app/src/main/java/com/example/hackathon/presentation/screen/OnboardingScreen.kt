package com.example.hackathon.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hackathon.R
import com.example.hackathon.ui.theme.HackathonTheme
import com.example.hackathon.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onNavigateToLogin: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier,
    ) { paddingValues ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                // 로고 영역 (중앙 상단)
                Box(
                    modifier =
                        Modifier
                            .size(120.dp)
                            .background(
                                Primary,
                                RoundedCornerShape(20.dp),
                            ),
                    contentAlignment = Alignment.Center,
                ) {
                    androidx.compose.foundation.Image(
                        painter = painterResource(R.drawable.ic_logo_rec),
                        contentDescription = "쩝쩝박사 로고",
                        modifier = Modifier.size(100.dp),
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                // 슬로건 텍스트 (2줄)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = "어디선가 들어본 바로 그 조합",
                        style = HackathonTheme.typography.Body_medium,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = "모두 쩝쩝박사에서",
                        style = HackathonTheme.typography.Body_medium,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                    )
                }

                Spacer(modifier = Modifier.height(60.dp))

                // 시작하기 버튼
                Button(
                    onClick = onNavigateToLogin,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = Primary,
                            contentColor = Color.White,
                        ),
                ) {
                    Text(
                        text = "시작하기",
                        style = HackathonTheme.typography.Sub1_semibold,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingScreenPreview() {
    OnboardingScreen()
}
