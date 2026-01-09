package com.example.hackathon.presentation.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.hackathon.core.component.CombinationCard
import com.example.hackathon.core.component.TopAppLogoBar
import com.example.hackathon.presentation.viewmodel.MyPageTab
import com.example.hackathon.presentation.viewmodel.MyPageViewModel
import com.example.hackathon.ui.theme.Gray50
import com.example.hackathon.ui.theme.Gray700
import com.example.hackathon.ui.theme.HackathonTheme
import com.example.hackathon.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreen(
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel(),
    onCombinationClick: (String) -> Unit = {},
    onLogout: () -> Unit = {},
    onChangeNickname: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isLoggedOut) {
        if (uiState.isLoggedOut) {
            onLogout()
        }
    }

    LaunchedEffect(uiState.selectedTab) {
        when (uiState.selectedTab) {
            MyPageTab.MY_RECIPES -> {
                if (uiState.myRecipes.isEmpty() && !uiState.isLoadingMyRecipes) {
                    viewModel.loadMyRecipes()
                }
            }
            MyPageTab.LIKED_COMBINATIONS -> {
                if (uiState.likedCombinations.isEmpty() && !uiState.isLoadingLikedCombinations) {
                    viewModel.loadLikedCombinations()
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White),
        ) {
            // 상단 로고
            TopAppLogoBar()

            // 프로필 섹션
            val user = uiState.user
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // 프로필 이미지
                Box(
                    modifier =
                        Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(
                                if (user?.profileImageUrl != null) {
                                    Color.Transparent
                                } else {
                                    Primary.copy(alpha = 0.2f)
                                },
                            ),
                    contentAlignment = Alignment.Center,
                ) {
                    if (user?.profileImageUrl != null) {
                        AsyncImage(
                            model = user.profileImageUrl,
                            contentDescription = "프로필 이미지",
                            modifier = Modifier.fillMaxSize(),
                        )
                    } else {
                        // 프로필 이미지가 없을 때 닉네임 첫 글자 표시
                        Text(
                            text = user?.nickname?.firstOrNull()?.toString() ?: "?",
                            style = HackathonTheme.typography.Head2_bold,
                            color = Color.White,
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // 닉네임 및 로그아웃
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = user?.nickname ?: "사용자",
                        style = HackathonTheme.typography.Head2_bold,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "로그아웃",
                        style = HackathonTheme.typography.Body_medium,
                        color = Gray700,
                        modifier = Modifier.clickable { viewModel.logout() },
                    )
                }

                // 닉네임 변경 버튼
                Button(
                    onClick = onChangeNickname,
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = Gray50,
                            contentColor = Color.Black,
                        ),
                    shape = RoundedCornerShape(15.dp),
                ) {
                    Text(
                        text = "닉네임 변경",
                        style = HackathonTheme.typography.Body_medium,
                    )
                }
            }

            Divider(color = Gray50, thickness = 1.dp)

            // 탭
            TabRow(
                selectedTabIndex = if (uiState.selectedTab == MyPageTab.MY_RECIPES) 0 else 1,
                containerColor = Color.White,
                contentColor = Primary,
            ) {
                Tab(
                    selected = uiState.selectedTab == MyPageTab.MY_RECIPES,
                    onClick = { viewModel.selectTab(MyPageTab.MY_RECIPES) },
                    text = {
                        Text(
                            text = "나의 레시피",
                            style = HackathonTheme.typography.Body_semibold,
                        )
                    },
                )
                Tab(
                    selected = uiState.selectedTab == MyPageTab.LIKED_COMBINATIONS,
                    onClick = { viewModel.selectTab(MyPageTab.LIKED_COMBINATIONS) },
                    text = {
                        Text(
                            text = "좋아요한 조합",
                            style = HackathonTheme.typography.Body_semibold,
                        )
                    },
                )
            }

            // 콘텐츠 영역
            when (uiState.selectedTab) {
                MyPageTab.MY_RECIPES -> {
                    if (uiState.isLoadingMyRecipes) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            androidx.compose.material3.CircularProgressIndicator()
                        }
                    } else if (uiState.myRecipes.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "아직 나만의 레시피가 없어요 ㅠㅠ",
                                style = HackathonTheme.typography.Body_medium,
                                color = Gray700,
                                textAlign = TextAlign.Center,
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            items(uiState.myRecipes) { combination ->
                                CombinationCard(
                                    combination = combination,
                                    onClick = { onCombinationClick(combination.id) },
                                )
                            }
                        }
                    }
                }
                MyPageTab.LIKED_COMBINATIONS -> {
                    if (uiState.isLoadingLikedCombinations) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            androidx.compose.material3.CircularProgressIndicator()
                        }
                    } else if (uiState.likedCombinations.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "아직 마음에 드는 조합이 없어요 ㅠㅠ",
                                style = HackathonTheme.typography.Body_medium,
                                color = Gray700,
                                textAlign = TextAlign.Center,
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            items(uiState.likedCombinations) { combination ->
                                CombinationCard(
                                    combination = combination,
                                    onClick = { onCombinationClick(combination.id) },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyScreenPreview() {
    MyScreen()
}
