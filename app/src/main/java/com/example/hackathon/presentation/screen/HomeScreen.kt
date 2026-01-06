package com.example.hackathon.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hackathon.core.component.CategoryChipRow
import com.example.hackathon.core.component.CombinationCard
import com.example.hackathon.core.component.SearchBar
import com.example.hackathon.domain.entity.Category
import com.example.hackathon.presentation.viewmodel.HomeViewModel

// 담당자: 예원
// TODO: 디자인 확인 후 UI 조정 필요
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onCombinationClick: (String) -> Unit = {},
    onCreateClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "조합 등록"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
        // 검색바
        SearchBar(
            query = searchQuery,
            onQueryChange = viewModel::updateSearchQuery,
            onSearch = { viewModel.loadCombinations() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 카테고리 필터
        CategoryChipRow(
            categories = listOf(Category.ALL, Category.SUBWAY, Category.HAIDILAO, Category.CONVENIENCE),
            selectedCategory = selectedCategory,
            onCategorySelected = viewModel::selectCategory
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 로딩/에러/리스트
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null -> {
                val errorMessage = uiState.error ?: "오류가 발생했습니다"
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.refresh() }) {
                        Text("다시 시도")
                    }
                }
            }
            uiState.combinations.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("등록된 조합이 없습니다")
                }
            }
            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.combinations) { combination ->
                        CombinationCard(
                            combination = combination,
                            onClick = { onCombinationClick(combination.id) }
                        )
                    }
                }
            }
        }
        }
    }
}



