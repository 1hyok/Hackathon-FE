package com.example.hackathon.core.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
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

private const val SHADOW_SIZE = 8

@Composable
fun CombinationCard(
    combination: Combination,
    onClick: () -> Unit,
    onLikeClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    // 🔹 shadow 파라미터
    val corner = 15.dp
    val shadowPad = 4.dp
    val shadowBlur = 6.dp
    val shadowOffsetX = 3.dp
    val shadowOffsetY = 3.dp
    val shadowAlpha = 0.15f

    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(
                    end = shadowBlur + shadowOffsetX,
                    bottom = shadowBlur + shadowOffsetY,
                )
                .drawBehind {
                    val left = shadowPad.toPx()
                    val top = shadowPad.toPx()
                    val right = size.width - shadowPad.toPx()
                    val bottom = size.height - shadowPad.toPx()

                    val paint = Paint().apply {
                        asFrameworkPaint().apply {
                            isAntiAlias = true
                            color = android.graphics.Color.TRANSPARENT

                            setShadowLayer(
                                shadowBlur.toPx(),
                                shadowOffsetX.toPx(),
                                shadowOffsetY.toPx(),
                                Color.Black.copy(alpha = shadowAlpha).toArgb(),
                            )
                        }
                    }

                    drawIntoCanvas { canvas ->
                        canvas.drawRoundRect(
                            left,
                            top,
                            right,
                            bottom,
                            corner.toPx(),
                            corner.toPx(),
                            paint,
                        )
                    }
                },
    ) {
        Card(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(corner),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                // 이미지
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = corner,
                                    topEnd = corner,
                                ),
                            ),
                ) {
                    if (combination.imageUrl != null) {
                        AsyncImage(
                            model = combination.imageUrl,
                            contentDescription = combination.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                        )
                    } else {
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

                // 콘텐츠
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                ) {
                    Spacer(modifier = Modifier.height(12.dp))

                    if (combination.tags.isNotEmpty()) {
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            combination.tags.forEachIndexed { index, tag ->
                                val isFirstTag = index == 0
                                Box(
                                    modifier =
                                        Modifier
                                            .then(
                                                if (isFirstTag) {
                                                    Modifier.background(
                                                        Primary,
                                                        RoundedCornerShape(15.dp),
                                                    )
                                                } else {
                                                    Modifier
                                                        .background(
                                                            Color.White,
                                                            RoundedCornerShape(15.dp),
                                                        )
                                                        .border(
                                                            width = 1.dp,
                                                            color = Primary,
                                                            shape = RoundedCornerShape(15.dp),
                                                        )
                                                },
                                            )
                                            .padding(horizontal = 10.dp, vertical = 4.dp),
                                ) {
                                    Text(
                                        text = tag,
                                        style = HackathonTheme.typography.Caption_medium,
                                        color = if (isFirstTag) Color.White else Primary,
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    Text(
                        text = combination.title,
                        style = HackathonTheme.typography.Head2_bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Black,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = combination.description,
                        style = HackathonTheme.typography.Body_medium,
                        color = Color(0xFFC0C0C0),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    HorizontalDivider(
                        thickness = 0.5.dp,
                        color = Color(0xFFC0C0C0),
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Row(
                            modifier =
                                if (onLikeClick != null) {
                                    Modifier.clickable(onClick = onLikeClick)
                                } else {
                                    Modifier
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            Icon(
                                imageVector =
                                    if (combination.isLiked) {
                                        Icons.Filled.Favorite
                                    } else {
                                        Icons.Outlined.FavoriteBorder
                                    },
                                contentDescription = "좋아요",
                                tint = Primary,
                                modifier = Modifier.size(18.dp),
                            )
                            Text(
                                text = String.format("%,d", combination.likeCount),
                                style = HackathonTheme.typography.Body_medium,
                                color = Color.Black,
                            )
                        }

                        Text(
                            text = combination.author.nickname,
                            style = HackathonTheme.typography.Body_medium,
                            color = Color.Black,
                        )
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true, name = "Liked State")
@Composable
private fun CombinationCardLikedPreview() {
    HackathonTheme {
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
}

@Preview(showBackground = true, name = "Not Liked State")
@Composable
private fun CombinationCardNotLikedPreview() {
    HackathonTheme {
        CombinationCard(
            combination =
                Combination(
                    id = "2",
                    title = "건희소스 (매콤달콤)",
                    description = "훠궈마스터 건희가 전수하는 매콤달콤 소스!! 둘이먹다 하...",
                    imageUrl = null,
                    category = Category.HAIDILAO,
                    ingredients = listOf("이탈리안 비엠티", "베이컨", "치즈"),
                    steps = listOf("빵 선택", "베이컨 추가", "치즈 추가"),
                    tags = listOf("#하이디라오", "#존맛탱소스", "#존맛탱소스"),
                    author = User(id = "1", nickname = "윤상00", profileImageUrl = null),
                    likeCount = 1120,
                    isLiked = false,
                    createdAt = System.currentTimeMillis().toString(),
                ),
            onClick = {},
        )
    }
}
