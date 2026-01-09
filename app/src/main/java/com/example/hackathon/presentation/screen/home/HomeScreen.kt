package com.example.hackathon.presentation.screen.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.hackathon.domain.entity.Category
import com.example.hackathon.domain.entity.Combination
import com.example.hackathon.domain.entity.User
import com.example.hackathon.presentation.viewmodel.HomeUiState
import com.example.hackathon.presentation.viewmodel.HomeViewModel
import com.example.hackathon.ui.theme.HackathonTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
    onCombinationClick: (String) -> Unit = {},
    onCreateClick: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            HomeTopBar(navController = navController)
        },
    ) { innerPadding ->
        HomeContent(
            uiState = uiState,
            selectedCategory = selectedCategory,
            onCategorySelected = { category ->
                viewModel.selectCategory(category)
            },
            onCombinationClick = onCombinationClick,
            onLoadMore = { viewModel.loadMore() },
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding),
        )
    }
}

@Preview(showBackground = true, name = "Home Screen")
@Composable
private fun HomeScreenPreview() {
    HackathonTheme {
        val mockUser = User(
            id = "user1",
            nickname = "테스트유저",
            profileImageUrl = null,
        )
        val mockCombinations =
            listOf(
                Combination(
                    id = "1",
                    title = "하이디라오 꿀조합",
                    description = "맛있는 하이디라오 조합입니다",
                    imageUrl = null,
                    category = Category.HAIDILAO,
                    ingredients = listOf("재료1", "재료2"),
                    steps = listOf("단계1", "단계2"),
                    tags = listOf("태그1", "태그2"),
                    author = mockUser,
                    likeCount = 10,
                    isLiked = false,
                    createdAt = "2024-01-01",
                ),
            )
        // ViewModel 없이 직접 상태를 전달하는 프리뷰
        Scaffold(
            topBar = {
                HomeTopBar(
                    onSearchClick = {},
                )
            },
        ) { innerPadding ->
            HomeContent(
                uiState = HomeUiState(combinations = mockCombinations),
                selectedCategory = Category.ALL,
                onCategorySelected = {},
                onCombinationClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
            )
        }
    }
}
