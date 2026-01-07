package com.example.hackathon.core.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.hackathon.domain.entity.Category
import com.example.hackathon.domain.entity.Combination
import com.example.hackathon.domain.entity.User

@Composable
fun CombinationCard(
    combination: Combination,
    onClick: () -> Unit,
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
            // 이미지
            if (combination.imageUrl != null) {
                AsyncImage(
                    model = combination.imageUrl,
                    contentDescription = combination.title,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

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

            // 하단 정보
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = combination.author.nickname,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                )

                Text(
                    text = "❤️ ${combination.likeCount}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
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
                title = "서브웨이 꿀조합",
                description = "이탈리안 비엠티에 베이컨 추가하면 완벽해요!",
                imageUrl = null,
                category = Category.SUBWAY,
                ingredients = listOf("이탈리안 비엠티", "베이컨", "치즈"),
                steps = listOf("빵 선택", "베이컨 추가", "치즈 추가"),
                author = User(id = "1", nickname = "테스트유저", profileImageUrl = null),
                likeCount = 42,
                createdAt = System.currentTimeMillis().toString(),
            ),
        onClick = {},
    )
}
