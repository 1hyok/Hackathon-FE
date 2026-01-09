package com.example.hackathon.presentation.screen.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.hackathon.domain.entity.Category
import com.example.hackathon.domain.entity.Combination
import com.example.hackathon.domain.entity.User
import com.example.hackathon.presentation.component.FilterBar
import com.example.hackathon.presentation.viewmodel.HomeUiState
import com.example.hackathon.ui.theme.HackathonTheme

@Composable
fun HomeContent(
    uiState: HomeUiState,
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit,
    onCombinationClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
    ) {
        item {
            FilterBar(
                categories = Category.entries.toList(),
                selectedCategory = selectedCategory,
                onCategorySelected = onCategorySelected,
            )
        }

        when {
            uiState.isLoading -> {
                HomeLoadingState()
            }

            uiState.error != null -> {
                HomeErrorState(error = uiState.error ?: "오류가 발생했습니다")
            }

            uiState.combinations.isEmpty() && !uiState.isLoading -> {
                HomeEmptyState()
            }

            else -> {
                // 조합 목록
                HomeCombinationList(
                    combinations = uiState.combinations,
                    onCombinationClick = onCombinationClick,
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
private fun HomeContentLoadingPreview() {
    HackathonTheme {
        HomeContent(
            uiState = HomeUiState(isLoading = true),
            selectedCategory = Category.ALL,
            onCategorySelected = {},
            onCombinationClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
private fun HomeContentErrorPreview() {
    HackathonTheme {
        HomeContent(
            uiState = HomeUiState(error = "네트워크 오류가 발생했습니다"),
            selectedCategory = Category.ALL,
            onCategorySelected = {},
            onCombinationClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Empty State")
@Composable
private fun HomeContentEmptyPreview() {
    HackathonTheme {
        HomeContent(
            uiState = HomeUiState(combinations = emptyList()),
            selectedCategory = Category.ALL,
            onCategorySelected = {},
            onCombinationClick = {},
        )
    }
}

@Preview(showBackground = true, name = "With Combinations")
@Composable
private fun HomeContentWithCombinationsPreview() {
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
                Combination(
                    id = "2",
                    title = "서브웨이 꿀조합",
                    description = "맛있는 서브웨이 조합입니다",
                    imageUrl = null,
                    category = Category.SUBWAY,
                    ingredients = listOf("재료1", "재료2"),
                    steps = listOf("단계1", "단계2"),
                    tags = listOf("태그1", "태그2"),
                    author = mockUser,
                    likeCount = 5,
                    isLiked = true,
                    createdAt = "2024-01-02",
                ),
            )
        HomeContent(
            uiState = HomeUiState(combinations = mockCombinations),
            selectedCategory = Category.ALL,
            onCategorySelected = {},
            onCombinationClick = {},
        )
    }
}
