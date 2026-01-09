package com.example.hackathon.presentation.screen.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .background(HackathonTheme.colors.white),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp, start = 20.dp, top = 30.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            SearchComponent(
                onSearchClick = {
                navController.navigate("search")
            },)
        }

        // TopAppLogoBar()
        LazyColumn {
            item {
                FilterBar(
                    modifier = Modifier.padding(top = 20.dp),
                    categories = Category.entries.toList(),
                    selectedCategory = selectedCategory,
                    onCategorySelected = { category ->
                        selectedCategory = category
                    },
                )
            }

            item {
            }
        }

        // TODO: 나중에 필터 따라 게시글 목룍 표시
    }
}
