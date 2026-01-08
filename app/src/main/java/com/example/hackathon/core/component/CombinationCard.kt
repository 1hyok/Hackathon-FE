package com.example.hackathon.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.hackathon.domain.entity.Category
import com.example.hackathon.domain.entity.Combination
import com.example.hackathon.domain.entity.User
import com.example.hackathon.ui.theme.Gray50

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
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
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
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            // 제목
            Text(
                text = combination.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 설명
            Text(
                text = combination.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 태그 표시
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
                                        MaterialTheme.colorScheme.primary,
                                        RoundedCornerShape(12.dp),
                                    )
                                    .padding(horizontal = 10.dp, vertical = 4.dp),
                        ) {
                            Text(
                                text = tag,
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // 하단 정보
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // 닉네임 (더 구분되게 스타일링)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                    )
                    Text(
                        text = combination.author.nickname,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                // 좋아요 버튼
                Row(
                    modifier =
                        Modifier
                            .then(
                                if (onLikeClick != null) {
                                    Modifier.clickable(onClick = onLikeClick)
                                } else {
                                    Modifier
                                },
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Box(
                        modifier =
                            Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(
                                    color =
                                        if (combination.isLiked) {
                                            Color.Red
                                        } else {
                                            Color.Transparent
                                        },
                                    shape = CircleShape,
                                )
                                .border(
                                    width = 1.5.dp,
                                    color =
                                        if (combination.isLiked) {
                                            Color.White
                                        } else {
                                            Color.Gray
                                        },
                                    shape = CircleShape,
                                ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector =
                                if (combination.isLiked) {
                                    Icons.Default.Favorite
                                } else {
                                    Icons.Outlined.Favorite
                                },
                            contentDescription = "좋아요",
                            tint =
                                if (combination.isLiked) {
                                    Color.White
                                } else {
                                    Color.Gray
                                },
                            modifier = Modifier.size(14.dp),
                        )
                    }
                    Text(
                        text = combination.likeCount.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color =
                            if (combination.isLiked) {
                                Color.Red
                            } else {
                                Color.Gray
                            },
                    )
                }
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
                title = "서브웨이 꿀조합",
                description = "이탈리안 비엠티에 베이컨 추가하면 완벽해요!",
                imageUrl = null,
                category = Category.SUBWAY,
                ingredients = listOf("이탈리안 비엠티", "베이컨", "치즈"),
                steps = listOf("빵 선택", "베이컨 추가", "치즈 추가"),
                tags = listOf("#서브웨이", "#테스트"),
                author = User(id = "1", nickname = "테스트유저", profileImageUrl = null),
                likeCount = 42,
                isLiked = false,
                createdAt = System.currentTimeMillis().toString(),
            ),
        onClick = {},
    )
}
