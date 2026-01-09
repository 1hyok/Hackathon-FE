package com.example.hackathon.presentation.screen.combination

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.hackathon.R
import com.example.hackathon.core.component.TopAppLogoBar
import com.example.hackathon.core.util.dropShadow
import com.example.hackathon.data.local.DummyData
import com.example.hackathon.presentation.viewmodel.DetailViewModel
import com.example.hackathon.ui.theme.HackathonTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    recipeId: Long,
    viewModel: DetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(recipeId) {
        if (recipeId > 0) {
            viewModel.loadRecipeDetail(recipeId)
        }
    }

    val recipe = uiState.recipeDetail

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .statusBarsPadding() // 상태바 침범 방지 (중요)
                    .height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.size(48.dp),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "뒤로가기",
                )
            }

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "logo",
                    modifier = Modifier.width(90.dp),
                )
            }

            // 오른쪽 더미 공간 (좌우 균형)
            Spacer(modifier = Modifier.size(48.dp))
        }
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(
                        brush =
                            Brush.verticalGradient(
                                colors =
                                    listOf(
                                        Color.Black.copy(alpha = 0.15f),
                                        Color.Transparent,
                                    ),
                            ),
                    ),
        )

        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp, top = 20.dp),
        ) {
            item {
                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(20.dp),
                        )
                    }

                    uiState.error != null -> {
                        Text(
                            text = uiState.error ?: "오류가 발생했습니다",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(20.dp),
                        )
                    }

                    recipe == null -> {
                        Text(
                            text = "레시피를 찾을 수 없습니다",
                            modifier = Modifier.padding(20.dp),
                        )
                    }

                    else -> {
                        if (recipe.images.isNotEmpty()) {
                            val pagerState = rememberPagerState { recipe.images.size }

                            Column {
                                HorizontalPager(
                                    state = pagerState,
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .height(260.dp),
                                ) { page ->
                                    AsyncImage(
                                        model = recipe.images[page],
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier =
                                            Modifier
                                                .fillMaxSize()
                                                .clip(RoundedCornerShape(16.dp)),
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                ) {
                                    repeat(recipe.images.size) { index ->
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
                        }

                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = recipe.title,
                                style = HackathonTheme.typography.Head2_bold,
                                color = HackathonTheme.colors.black,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Icon(
                                imageVector =
                                    if (recipe.userInteraction.isLiked) {
                                        Icons.Filled.Favorite
                                    } else {
                                        Icons.Outlined.FavoriteBorder
                                    },
                                contentDescription = "좋아요",
                                tint =
                                    if (recipe.userInteraction.isLiked) {
                                        HackathonTheme.colors.primary
                                    } else {
                                        HackathonTheme.colors.gray50
                                    },
                                modifier =
                                    Modifier
                                        .size(20.dp)
                                        .clickable {
                                            viewModel.toggleLike()
                                        },
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = recipe.stats.likesCount.toString(),
                                style = HackathonTheme.typography.Body_medium,
                                color = HackathonTheme.colors.gray700,
                            )
                        }

                        val authorText = DummyData.dummyUser.nickname

                        Text(
                            text = authorText,
                            style = HackathonTheme.typography.Body_medium,
                            color = Color(0xFF555555),
                            modifier = Modifier.padding(bottom = 10.dp),
                        )

                        if (recipe.tags.isNotEmpty()) {
                            LazyRow(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 12.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                items(recipe.tags) { tag ->
                                    Box(
                                        modifier =
                                            Modifier
                                                .background(
                                                    color = HackathonTheme.colors.primary,
                                                    shape = RoundedCornerShape(20.dp),
                                                )
                                                .padding(horizontal = 12.dp, vertical = 4.dp),
                                    ) {
                                        Text(
                                            text = tag,
                                            style = HackathonTheme.typography.Caption_medium,
                                            color = HackathonTheme.colors.white,
                                        )
                                    }
                                }
                            }
                        }

                        Text(
                            text = recipe.description,
                            style = HackathonTheme.typography.Caption_medium,
                            color = HackathonTheme.colors.gray700,
                            modifier = Modifier.padding(vertical = 15.dp),
                        )

                        Column(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .shadow(
                                        elevation = 6.dp,
                                        shape = RoundedCornerShape(10.dp),
                                    )
                                    .background(
                                        Color(0xFFF7F7F7),
                                        shape = RoundedCornerShape(10.dp),
                                    )
                                    .border(
                                        shape = RoundedCornerShape(10.dp),
                                        color = Color(0xFFCCCCCC),
                                        width = 1.dp,
                                    )
                                    .padding(horizontal = 20.dp, vertical = 10.dp),
                        ) {
                            Text(
                                text = authorText + "님의 레시피",
                                style = HackathonTheme.typography.Sub1_semibold,
                                color = HackathonTheme.colors.black,
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            recipe.ingredients.forEach {
                                Text(
                                    text = "• ${it.name} ${it.amount}",
                                    style = HackathonTheme.typography.Body_medium,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
