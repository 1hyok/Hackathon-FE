package com.example.hackathon.feature.combination.component

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.hackathon.core.designsystem.R as DesignSystemR
import com.example.hackathon.core.designsystem.icon.HackathonIcons
import com.example.hackathon.core.designsystem.theme.Gray50
import com.example.hackathon.core.designsystem.theme.Gray700
import com.example.hackathon.core.designsystem.theme.HackathonTheme
import com.example.hackathon.feature.combination.R

@Composable
fun ImageUploadSection(
    imageUris: List<Uri>,
    onImageClick: () -> Unit,
    onRemoveImage: (Uri) -> Unit,
    modifier: Modifier = Modifier
) {
    // drawBehind 는 비-Composable(DrawScope)이라 @Composable 게터인 HackathonTheme.colors 를 직접 못 쓴다.
    // 색을 Composable 본문에서 미리 읽어 호이스팅한다.
    val primaryColor = HackathonTheme.colors.primary
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // 메인 이미지 업로드 영역
        if (imageUris.isEmpty()) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .drawBehind {
                            val strokeWidth = 2.dp.toPx()
                            val dashWidth = 6.dp.toPx()
                            val dashGap = 6.dp.toPx()

                            drawRoundRect(
                                color = primaryColor.copy(alpha = 0.3f),
                                style =
                                    Stroke(
                                        width = strokeWidth,
                                        pathEffect =
                                            PathEffect.dashPathEffect(
                                                floatArrayOf(dashWidth, dashGap)
                                            )
                                    ),
                                cornerRadius = CornerRadius(12.dp.toPx())
                            )
                        }.background(
                            color = HackathonTheme.colors.primaryContainer,
                            shape = RoundedCornerShape(12.dp)
                        ).clickable { onImageClick() },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(DesignSystemR.drawable.ic_camera),
                        contentDescription = stringResource(R.string.combination_image_upload_add),
                        tint = Gray700,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = stringResource(R.string.combination_image_upload_label),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gray700
                    )
                }
            }
        } else {
            // 메인 이미지 미리보기
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(180.dp)
            ) {
                AsyncImage(
                    model = imageUris.first(),
                    contentDescription = stringResource(R.string.combination_image_selected),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                // 이미지 제거 버튼
                IconButton(
                    onClick = { onRemoveImage(imageUris.first()) },
                    modifier =
                        Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                ) {
                    Box(
                        modifier =
                            Modifier
                                .size(32.dp)
                                .background(
                                    Color.Black.copy(alpha = 0.6f),
                                    CircleShape
                                ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = HackathonIcons.Close,
                            contentDescription = stringResource(R.string.combination_image_remove),
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // 썸네일 영역 (나머지 이미지들)
            if (imageUris.size > 1) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    imageUris.drop(1).forEach { uri ->
                        Box(
                            modifier =
                                Modifier
                                    .weight(1f)
                                    .height(60.dp)
                                    .clip(RoundedCornerShape(8.dp))
                        ) {
                            AsyncImage(
                                model = uri,
                                contentDescription = stringResource(
                                    R.string.combination_image_thumbnail
                                ),
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            IconButton(
                                onClick = { onRemoveImage(uri) },
                                modifier =
                                    Modifier
                                        .align(Alignment.TopEnd)
                                        .size(20.dp)
                            ) {
                                Icon(
                                    imageVector = HackathonIcons.Close,
                                    contentDescription = stringResource(
                                        R.string.combination_image_remove
                                    ),
                                    tint = Color.White,
                                    modifier =
                                        Modifier
                                            .size(16.dp)
                                            .background(
                                                Color.Black.copy(alpha = 0.6f),
                                                CircleShape
                                            ).padding(4.dp)
                                )
                            }
                        }
                    }
                    // 추가 이미지 업로드 버튼 (5장 미만일 때)
                    if (imageUris.size < 5) {
                        Box(
                            modifier =
                                Modifier
                                    .weight(1f)
                                    .height(60.dp)
                                    .background(Gray50, RoundedCornerShape(8.dp))
                                    .border(
                                        1.dp,
                                        Gray700.copy(alpha = 0.3f),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable { onImageClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = HackathonIcons.Add,
                                contentDescription = stringResource(R.string.combination_image_add),
                                tint = Gray700,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            } else if (imageUris.size < 5) {
                // 첫 번째 이미지만 있을 때 추가 버튼
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(4) {
                        Box(
                            modifier =
                                Modifier
                                    .weight(1f)
                                    .height(60.dp)
                                    .background(Gray50, RoundedCornerShape(8.dp))
                                    .border(
                                        1.dp,
                                        Gray700.copy(alpha = 0.3f),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable { onImageClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = HackathonIcons.Add,
                                contentDescription = stringResource(R.string.combination_image_add),
                                tint = Gray700,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ImageUploadSectionPreview() {
    ImageUploadSection(
        imageUris = emptyList(),
        onImageClick = {},
        onRemoveImage = {}
    )
}
