package com.example.hackathon.presentation.screen.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.hackathon.domain.entity.Category
import com.example.hackathon.presentation.screen.home.component.FilterBar
import com.example.hackathon.presentation.screen.home.component.SearchComponent
import com.example.hackathon.presentation.viewmodel.HomeViewModel
import com.example.hackathon.ui.theme.HackathonTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
    onCombinationClick: (String) -> Unit = {},
    onCreateClick: () -> Unit = {},
) {
    // 최소 UI 유지: 앱이 크래시 되지 않도록 안전한 플레이스홀더만 표시
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var selectedCategory by rememberSaveable {
        mutableStateOf(Category.ALL)
    }

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
                        selectedCategory = category
                    },
                )
            }
            /*
            item {
                when {
                    uiState.isLoading -> {
                        Box(
                            modifier =
                                Modifier
                                    .fillParentMaxHeight()
                                    .padding(top = 40.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    uiState.error != null -> {
                        Box(
                            modifier =
                                Modifier
                                    .fillParentMaxHeight()
                                    .padding(top = 40.dp),
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

                    uiState.combinations.isEmpty() -> {
                        Box(
                            modifier =
                                Modifier
                                    .fillParentMaxHeight()
                                    .padding(top = 40.dp),
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

                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            items(uiState.combinations) { combination ->
                                CombinationCard(
                                    combination = combination,
                                    onClick = { onCombinationClick(combination.id) },
                                )
                            }
                        }
                    }
                }
            }*/
        }
    }
}
