package com.example.hackathon.presentation.screen

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hackathon.core.component.CombinationCard
import com.example.hackathon.presentation.viewmodel.MyPageViewModel
import com.example.hackathon.ui.theme.Primary

// 담당자: 일혁
// TODO: 사용자 정보 API 연동 필요
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreen(
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel(),
    onCombinationClick: (String) -> Unit = {},
    onLogout: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val myCombinations = uiState.myCombinations
    val likedCombinations = uiState.likedCombinations
    val currentCombinations =
        if (selectedTab == com.example.hackathon.presentation.viewmodel.MyPageTab.MY_COMBINATIONS) {
            myCombinations
        } else {
            likedCombinations
        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "마이페이지",
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: 뒤로가기 연결 필요시 구현 */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
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
        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            // 프로필 섹션
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
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
                                text = "쩝쩝박사".take(1),
                                style = MaterialTheme.typography.titleLarge,
                            )
                        }
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "쩝쩝박사",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = "내가 등록한 조합 ${myCombinations.size}개",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        )
                    }
                }
            }

            // 로그아웃 버튼
            Button(
                onClick = {
                    viewModel.logout()
                    onLogout()
                },
                modifier = Modifier.fillMaxWidth(),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError,
                    ),
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("로그아웃")
            }

            // 탭
            TabRow(
                selectedTabIndex =
                    if (selectedTab == com.example.hackathon.presentation.viewmodel.MyPageTab.MY_COMBINATIONS) {
                        0
                    } else {
                        1
                    },
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ) {
                Tab(
                    selected = selectedTab == com.example.hackathon.presentation.viewmodel.MyPageTab.MY_COMBINATIONS,
                    onClick = { viewModel.selectTab(com.example.hackathon.presentation.viewmodel.MyPageTab.MY_COMBINATIONS) },
                    text = { Text("내가 등록한 조합") },
                )
                Tab(
                    selected = selectedTab == com.example.hackathon.presentation.viewmodel.MyPageTab.LIKED_COMBINATIONS,
                    onClick = { viewModel.selectTab(com.example.hackathon.presentation.viewmodel.MyPageTab.LIKED_COMBINATIONS) },
                    text = { Text("좋아요한 조합") },
                )
            }

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.error != null -> {
                    val errorMessage = uiState.error ?: "오류가 발생했습니다"
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                        )
                        Button(onClick = { viewModel.refresh() }) {
                            Text("다시 시도")
                        }
                    }
                }

                currentCombinations.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text =
                                if (selectedTab == com.example.hackathon.presentation.viewmodel.MyPageTab.MY_COMBINATIONS) {
                                    "등록한 조합이 없습니다"
                                } else {
                                    "좋아요한 조합이 없습니다"
                                },
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(currentCombinations) { combination ->
                            CombinationCard(
                                combination = combination,
                                onClick = { onCombinationClick(combination.id) },
                                onLikeClick = { viewModel.toggleLike(combination.id) },
                            )
                        }
                    }
                }
            }
        }
    }
}
