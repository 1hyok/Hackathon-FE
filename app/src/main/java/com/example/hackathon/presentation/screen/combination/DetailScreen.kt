package com.example.hackathon.presentation.screen.combination

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
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
        TopAppLogoBar()

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

        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
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
                                        Color(0xFFD9D9D9),
                                        shape = RoundedCornerShape(10.dp),
                                    )
                                    .border(
                                        shape = RoundedCornerShape(10.dp),
                                        color = HackathonTheme.colors.gray700,
                                        width = 1.dp,
                                    )
                                    .padding(horizontal = 20.dp, vertical = 10.dp),
                        ) {
                            Text(
                                text = "재료",
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
