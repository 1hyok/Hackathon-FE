package com.example.hackathon.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.hackathon.domain.entity.Category
import com.example.hackathon.domain.entity.Combination
import com.example.hackathon.domain.entity.User
import com.example.hackathon.ui.theme.Gray50
import com.example.hackathon.ui.theme.Gray700
import com.example.hackathon.ui.theme.HackathonTheme
import com.example.hackathon.ui.theme.Primary

@Composable
fun CombinationCard(
    combination: Combination,
    onClick: () -> Unit,
    // 좋아요 클릭 콜백 (null이면 비활성화)
    onLikeClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        ) {
            // 이미지 (항상 표시)
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
            ) {
                if (combination.imageUrl != null) {
                    AsyncImage(
                        model = combination.imageUrl,
                        contentDescription = combination.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    // 플레이스홀더 이미지
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .background(Gray50),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "이미지 없음",
                            style = HackathonTheme.typography.Body_medium,
                            color = Gray700.copy(alpha = 0.5f),
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 태그 표시 (이미지 바로 아래)
            if (combination.tags.isNotEmpty()) {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    combination.tags.forEach { tag ->
                        Box(
                            modifier =
                                Modifier
                                    .background(
                                        Primary,
                                        RoundedCornerShape(15.dp),
                                    )
                                    .padding(horizontal = 10.dp, vertical = 4.dp),
                        ) {
                            Text(
                                text = tag,
                                style = HackathonTheme.typography.Caption_medium,
                                color = Color.White,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // 제목
            Text(
                text = combination.title,
                style = HackathonTheme.typography.Sub1_semibold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 설명
            Text(
                text = combination.description,
                style = HackathonTheme.typography.Body_medium,
                color = Gray700,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 하단 정보: 좋아요 (왼쪽), 작성자 (오른쪽)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // 좋아요 (왼쪽)
                Row(
                    modifier =
                        Modifier
                            .then(
                                if (onLikeClick != null) {
                                    Modifier.clickable(onClick = onLikeClick)
                                } else {
                                    Modifier
                                },
                            ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "좋아요",
                        tint = Primary,
                        modifier = Modifier.size(18.dp),
                    )
                    Text(
                        text = combination.likeCount.toString(),
                        style = HackathonTheme.typography.Body_medium,
                        color = Primary,
                    )
                }

                // 작성자 닉네임 (오른쪽)
                Text(
                    text = combination.author.nickname,
                    style = HackathonTheme.typography.Body_medium,
                    color = Gray700,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CombinationCardPreview() {
    CombinationCard(
        combination =
            Combination(
                id = "1",
                title = "건희소스 (매콤달콤)",
                description = "훠궈마스터 건희가 전수하는 매콤달콤 소스!! 둘이먹다 하...",
                imageUrl = null,
                category = Category.SUBWAY,
                ingredients = listOf("이탈리안 비엠티", "베이컨", "치즈"),
                steps = listOf("빵 선택", "베이컨 추가", "치즈 추가"),
                tags = listOf("#하이디라오", "#존맛탱소스", "#존맛탱소스"),
                author = User(id = "1", nickname = "윤상00", profileImageUrl = null),
                likeCount = 1120,
                isLiked = true,
                createdAt = System.currentTimeMillis().toString(),
            ),
        onClick = {},
    )
}
