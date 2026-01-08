package com.example.hackathon.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.hackathon.core.component.TopAppLogoBar
import com.example.hackathon.presentation.viewmodel.DetailViewModel
import com.example.hackathon.ui.theme.HackathonTheme

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
        /** Top Bar */
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

        /** Content */
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
                    // Todo: Combination ID 에 해당하는 url로 그냥 교체
                    val imageUrls =
                        listOf(
                            "https://picsum.photos/600/600?1",
                            "https://picsum.photos/600/600?2",
                            "https://picsum.photos/600/600?3",
                        )

                    val pagerState = rememberPagerState { imageUrls.size }

                    Column {
                        /** 이미지 슬라이더 */
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

                        /** 페이지 인디케이터 (점) */
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

                    /** 해시태그 및 좋아요 **/
                }
            }
        }
    }
}
