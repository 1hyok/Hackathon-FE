package com.example.hackathon.presentation.screen.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.hackathon.core.component.CombinationCard
import com.example.hackathon.domain.entity.Category
import com.example.hackathon.presentation.screen.home.component.FilterBar
import com.example.hackathon.presentation.screen.home.component.SearchComponent
import com.example.hackathon.presentation.viewmodel.HomeViewModel
import com.example.hackathon.ui.theme.Gray700
import com.example.hackathon.ui.theme.HackathonTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
    onCombinationClick: (String) -> Unit = {},
    onCreateClick: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 6.dp,
                            clip = false,
                        )
                        .background(HackathonTheme.colors.white)
                        .padding(top = 30.dp, bottom = 20.dp),
            ) {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, top = 25.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    SearchComponent(
                        onSearchClick = {
                            navController.navigate("search")
                        },
                    )
                }
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier =
                modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
        ) {
            item {
                FilterBar(
                    categories = Category.entries.toList(),
                    selectedCategory = selectedCategory,
                    onCategorySelected = { category ->
                        viewModel.selectCategory(category)
                    },
                )
            }

            when {
                uiState.isLoading -> {
                    item {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 40.dp, bottom = 200.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                uiState.error != null -> {
                    item {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 40.dp, bottom = 200.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = uiState.error ?: "오류가 발생했습니다",
                                style = HackathonTheme.typography.Body_medium,
                                color = Gray700,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }

                uiState.combinations.isEmpty() && !uiState.isLoading -> {
                    item {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 40.dp, bottom = 200.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "아직 등록된 조합이 없어요 ㅠㅠ",
                                style = HackathonTheme.typography.Body_medium,
                                color = Gray700,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }

                else -> {
                    // 조합 목록
                    items(uiState.combinations) { combination ->
                        CombinationCard(
                            combination = combination,
                            onClick = { onCombinationClick(combination.id) },
                            onLikeClick = { viewModel.toggleLike(combination.id) },
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                        )
                    }
                }
            }
        }
    }
}
