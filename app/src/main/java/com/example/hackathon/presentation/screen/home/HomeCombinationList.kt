package com.example.hackathon.presentation.screen.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hackathon.core.component.CombinationCard
import com.example.hackathon.domain.entity.Category
import com.example.hackathon.domain.entity.Combination
import com.example.hackathon.domain.entity.User
import com.example.hackathon.ui.theme.HackathonTheme

fun LazyListScope.HomeCombinationList(
    combinations: List<Combination>,
    onCombinationClick: (String) -> Unit,
) {
    items(combinations) { combination ->
        CombinationCard(
            combination = combination,
            onClick = { onCombinationClick(combination.id) },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
        )
    }
}

@Preview(showBackground = true, name = "Combination List")
@Composable
private fun HomeCombinationListPreview() {
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
        androidx.compose.foundation.lazy.LazyColumn {
            HomeCombinationList(
                combinations = mockCombinations,
                onCombinationClick = {},
            )
        }
    }
}
