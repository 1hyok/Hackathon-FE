package com.example.hackathon.presentation.screen.component

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.hackathon.ui.theme.Gray50
import com.example.hackathon.ui.theme.Gray700

@Composable
fun ImageUploadSection(
    imageUris: List<Uri>,
    onImageClick: () -> Unit,
    onRemoveImage: (Uri) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // 메인 이미지 업로드 영역
        if (imageUris.isEmpty()) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(Gray50, RoundedCornerShape(12.dp))
                        .border(1.dp, Gray700.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                        .clickable { onImageClick() },
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "이미지 등록",
                        tint = Gray700,
                        modifier = Modifier.size(32.dp),
                    )
                    Text(
                        text = "사진 등록 (최대 5장)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gray700,
                    )
                }
            }
        } else {
            // 메인 이미지 미리보기
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(180.dp),
            ) {
                AsyncImage(
                    model = imageUris.first(),
                    contentDescription = "선택한 이미지",
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                )
                // 이미지 제거 버튼
                IconButton(
                    onClick = { onRemoveImage(imageUris.first()) },
                    modifier =
                        Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp),
                ) {
                    Box(
                        modifier =
                            Modifier
                                .size(32.dp)
                                .background(
                                    Color.Black.copy(alpha = 0.6f),
                                    CircleShape,
                                ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "이미지 제거",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                }
            }

            // 썸네일 영역 (나머지 이미지들)
            if (imageUris.size > 1) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    imageUris.drop(1).forEach { uri ->
                        Box(
                            modifier =
                                Modifier
                                    .weight(1f)
                                    .height(60.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                        ) {
                            AsyncImage(
                                model = uri,
                                contentDescription = "썸네일",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                            )
                            IconButton(
                                onClick = { onRemoveImage(uri) },
                                modifier =
                                    Modifier
                                        .align(Alignment.TopEnd)
                                        .size(20.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "이미지 제거",
                                    tint = Color.White,
                                    modifier =
                                        Modifier
                                            .size(16.dp)
                                            .background(
                                                Color.Black.copy(alpha = 0.6f),
                                                CircleShape,
                                            )
                                            .padding(4.dp),
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
                                    .border(1.dp, Gray700.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                    .clickable { onImageClick() },
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "이미지 추가",
                                tint = Gray700,
                                modifier = Modifier.size(24.dp),
                            )
                        }
                    }
                }
            } else if (imageUris.size < 5) {
                // 첫 번째 이미지만 있을 때 추가 버튼
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    repeat(4) {
                        Box(
                            modifier =
                                Modifier
                                    .weight(1f)
                                    .height(60.dp)
                                    .background(Gray50, RoundedCornerShape(8.dp))
                                    .border(1.dp, Gray700.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                    .clickable { onImageClick() },
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "이미지 추가",
                                tint = Gray700,
                                modifier = Modifier.size(24.dp),
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
        onRemoveImage = {},
    )
}
