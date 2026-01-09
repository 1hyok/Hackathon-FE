package com.example.hackathon.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hackathon.presentation.viewmodel.MyPageViewModel
import com.example.hackathon.ui.theme.Primary

// 담당자: 일혁
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreen(
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel(),
    onEditProfileClick: () -> Unit = {},
    onLogout: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // 화면이 표시될 때마다 프로필 정보 새로고침
    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "내 정보",
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                },
                actions = {
                    IconButton(onClick = { /* TODO: 설정 화면 */ }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "설정",
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = Primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(0.dp),
        ) {
            // 프로필 섹션
            item {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Surface(
                            modifier = Modifier.size(64.dp),
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    text = uiState.user?.nickname?.take(1) ?: "?",
                                    style = MaterialTheme.typography.titleLarge,
                                )
                            }
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text(
                                text = "반갑습니다, ${uiState.user?.nickname ?: "사용자"} 님!",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                            TextButton(
                                onClick = onEditProfileClick,
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                            ) {
                                Text(
                                    text = "회원정보 수정",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Primary,
                                )
                            }
                        }
                    }
                }
            }

            // 주문 내역 섹션
            item {
                HorizontalDivider()
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable { /* TODO: 주문 내역 화면으로 이동 */ }
                            .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "주문 내역",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    )
                }
                HorizontalDivider()
            }

            // 메뉴 항목들
            val menuItems =
                listOf(
                    "공지사항",
                    "이벤트",
                    "자주 묻는 질문",
                    "1:1 문의",
                    "앱 설정",
                    "개인정보 처리방침",
                    "서비스 이용약관",
                )

            items(menuItems) { menuItem ->
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable { /* TODO: 각 메뉴 화면으로 이동 */ }
                            .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = menuItem,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    )
                }
                HorizontalDivider()
            }

            // 버전 정보
            item {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "버전 정보",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = "v1.0.0",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    )
                }
                HorizontalDivider()
            }

            // 로그아웃 버튼
            item {
                Button(
                    onClick = {
                        viewModel.logout()
                        onLogout()
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError,
                        ),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("로그아웃")
                }
            }
        }
    }
}
