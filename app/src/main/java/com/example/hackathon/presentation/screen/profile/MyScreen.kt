package com.example.hackathon.presentation.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.hackathon.R
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
        contentWindowInsets = WindowInsets(0),
    ) { paddingValues ->

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White),
        ) {
            Column {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(80.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_logo),
                            contentDescription = "logo",
                            modifier = Modifier.width(90.dp),
                        )
                }

                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .background(
                                brush =
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Black.copy(alpha = 0.15f),
                                            Color.Transparent,
                                        ),
                                    ),
                            ),
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {

                item {
                    val user = uiState.user
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
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
                                Text(
                                    text = user?.nickname?.firstOrNull()?.toString() ?: "?",
                                    style = HackathonTheme.typography.Head2_bold,
                                    color = Color.White,
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = user?.nickname ?: "사용자",
                                style = HackathonTheme.typography.Head2_bold,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "로그아웃",
                                style = HackathonTheme.typography.Body_medium,
                                color = Gray700,
                                modifier = Modifier.clickable { viewModel.logout() },
                            )
                        }

                        Button(
                            onClick = onChangeNickname,
                            colors =
                                ButtonDefaults.buttonColors(
                                    containerColor = Gray50,
                                    contentColor = Color.Black,
                                ),
                            shape = RoundedCornerShape(15.dp),
                        ) {
                            Text("닉네임 변경")
                        }
                    }
                }

                item { Divider(color = Gray50, thickness = 1.dp) }

                item {
                    TabRow(
                        selectedTabIndex =
                            if (uiState.selectedTab == MyPageTab.MY_RECIPES) 0 else 1,
                    ) {
                        Tab(
                            selected = uiState.selectedTab == MyPageTab.MY_RECIPES,
                            onClick = { viewModel.selectTab(MyPageTab.MY_RECIPES) },
                            text = { Text("나의 레시피") },
                        )
                        Tab(
                            selected = uiState.selectedTab == MyPageTab.LIKED_COMBINATIONS,
                            onClick = { viewModel.selectTab(MyPageTab.LIKED_COMBINATIONS) },
                            text = { Text("좋아요한 조합") },
                        )
                    }
                }
                item { Spacer(Modifier.height(15.dp)) }

                when (uiState.selectedTab) {
                    MyPageTab.MY_RECIPES -> {
                        items(uiState.myRecipes) { combination ->
                            Box(
                                modifier = Modifier.padding(horizontal = 10.dp),
                            ) {
                                CombinationCard(
                                    combination = combination,
                                    onClick = { onCombinationClick(combination.id) },
                                )
                            }
                        }
                    }

                    MyPageTab.LIKED_COMBINATIONS -> {
                        items(uiState.likedCombinations) { combination ->
                            Box(
                                modifier = Modifier.padding(horizontal = 10.dp),
                            ) {
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
