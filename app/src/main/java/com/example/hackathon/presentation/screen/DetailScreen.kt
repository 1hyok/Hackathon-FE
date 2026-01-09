package com.example.hackathon.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.hackathon.core.component.CategoryChip
import com.example.hackathon.core.component.TopAppLogoBar
import com.example.hackathon.domain.entity.Category
import com.example.hackathon.presentation.viewmodel.DetailViewModel
import com.example.hackathon.ui.theme.HackathonTheme

// 담당자: 예원
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    combinationId: String = "",
    viewModel: DetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(combinationId) {
        if (combinationId.isNotBlank()) {
            viewModel.loadCombination(combinationId)
        }
    }

    val combination = uiState.combination

    Column(
        modifier =
            modifier
                .fillMaxSize(),
    ) {
        TopAppLogoBar()

        // Top Bar
        TopAppBar(
            title = {
                Text(
                    text = "레시피 상세",
                    style = HackathonTheme.typography.Sub1_semibold,
                    color = HackathonTheme.colors.black,
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "뒤로가기",
                    )
                }
            },
            windowInsets = WindowInsets(0),
        )

        // Content
        Column(
            modifier =
                Modifier
                    .fillMaxSize(),
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }

                uiState.error != null -> {
                    Text(
                        text = uiState.error ?: "오류가 발생했습니다",
                        color = MaterialTheme.colorScheme.error,
                    )
                }

                combination == null -> {
                    Text("조합을 찾을 수 없습니다")
                }

                else -> {
                    // TODO: Combination ID 에 해당하는 url로 교체
                    val imageUrls =
                        listOf(
                            "https://picsum.photos/600/600?1",
                            "https://picsum.photos/600/600?2",
                            "https://picsum.photos/600/600?3",
                        )

                    val pagerState = rememberPagerState { imageUrls.size }

                    Column {
                        // 이미지 슬라이더
                        HorizontalPager(
                            state = pagerState,
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(260.dp)
                                    .padding(horizontal = 20.dp),
                        ) { page ->
                            AsyncImage(
                                model = imageUrls[page],
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier =
                                    Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(16.dp)),
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // 페이지 인디케이터
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            repeat(imageUrls.size) { index ->
                                Box(
                                    modifier =
                                        Modifier
                                            .padding(4.dp)
                                            .size(
                                                if (pagerState.currentPage == index) 8.dp else 6.dp,
                                            )
                                            .clip(CircleShape)
                                            .background(
                                                if (pagerState.currentPage == index) {
                                                    HackathonTheme.colors.primary
                                                } else {
                                                    HackathonTheme.colors.gray50
                                                },
                                            ),
                                )
                            }
                        }
                    }

                    // 좋아요 Row
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = combination?.title ?: "",
                            style = HackathonTheme.typography.Head2_bold,
                            color = HackathonTheme.colors.black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )

                        Icon(
                            imageVector =
                                if (combination?.isLiked == true) {
                                    Icons.Filled.Favorite
                                } else {
                                    Icons.Outlined.FavoriteBorder
                                },
                            contentDescription = "좋아요",
                            tint =
                                if (combination?.isLiked == true) {
                                    HackathonTheme.colors.primary
                                } else {
                                    HackathonTheme.colors.gray50
                                },
                            modifier =
                                Modifier
                                    .size(20.dp)
                                    .clickable(
                                        enabled = combination != null,
                                    ) {
                                        viewModel.toggleLike()
                                    },
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = combination?.likeCount?.toString() ?: "0",
                            style = HackathonTheme.typography.Body_medium,
                            color = HackathonTheme.colors.gray700,
                        )
                    }

                    // TODO: 나중에 카테고리가 아닌 해시태그로 교체
                    val categories: List<Category> =
                        listOf(
                            Category.HAIDILAO,
                            Category.SUBWAY,
                            Category.CONVENIENCE,
                        )

                    LazyRow(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(categories) { category ->
                            CategoryChip(
                                category = category,
                                isSelected = true,
                                onClick = {},
                            )
                        }
                    }

                    Text(
                        text = "훠궈 마스터 건희가 전수하는 매콤달콤 소스! 둘이서 먹다가 하나 죽어도 모르는 바로 그 맛.",
                        style = HackathonTheme.typography.Caption_medium,
                        color = HackathonTheme.colors.gray700,
                        modifier =
                            Modifier
                                .padding(vertical = 15.dp),
                    )
                    Column(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            // TODO: api 연동 시 닉네임으로 교체
                            text = "윤상00님의 레시피",
                            style = HackathonTheme.typography.Sub1_semibold,
                            color = HackathonTheme.colors.black,
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailScreenPreview() {
    DetailScreen()
}
