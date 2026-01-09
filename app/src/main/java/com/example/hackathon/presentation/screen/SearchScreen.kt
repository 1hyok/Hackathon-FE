package com.example.hackathon.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hackathon.core.component.CombinationCard
import com.example.hackathon.presentation.viewmodel.SearchViewModel
import com.example.hackathon.ui.theme.Gray700
import com.example.hackathon.ui.theme.Primary
import kotlinx.coroutines.delay

// 담당자: 일혁
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onCombinationClick: (String) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchTextFieldValue =
        androidx.compose.runtime.remember {
            androidx.compose.runtime.mutableStateOf(TextFieldValue(uiState.searchQuery))
        }

    androidx.compose.runtime.LaunchedEffect(uiState.searchQuery) {
        searchTextFieldValue.value = TextFieldValue(uiState.searchQuery)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    OutlinedTextField(
                        value = searchTextFieldValue.value,
                        onValueChange = { newValue ->
                            searchTextFieldValue.value = newValue
                            viewModel.updateSearchQuery(newValue.text)
                        },
                        placeholder = {
                            Text(
                                text = "검색어를 입력해주세요",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(15.dp),
                        colors =
                            OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Primary,
                                unfocusedBorderColor = Gray700,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                            ),
                        singleLine = true,
                        trailingIcon = {
                            if (searchTextFieldValue.value.text.isNotEmpty()) {
                                IconButton(
                                    onClick = {
                                        searchTextFieldValue.value = TextFieldValue("")
                                        viewModel.updateSearchQuery("")
                                    },
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "검색어 지우기",
                                        tint = Gray700,
                                    )
                                }
                            }
                        },
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = Color.White,
                        )
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = Primary,
                    ),
            )
        },
        modifier = modifier,
    ) { paddingValues ->
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if (uiState.searchQuery.isEmpty()) {
                // 최근 검색어 섹션
                if (uiState.recentSearches.isNotEmpty()) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = "최근 검색어",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Black,
                            )
                            TextButton(
                                onClick = { viewModel.clearRecentSearches() },
                                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                            ) {
                                Text(
                                    text = "전체 삭제",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Gray700,
                                )
                            }
                        }
                    }
                    items(uiState.recentSearches) { searchTerm ->
                        Row(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.updateSearchQuery(searchTerm)
                                        viewModel.search(searchTerm)
                                    }
                                    .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = searchTerm,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black,
                            )
                            IconButton(
                                onClick = { viewModel.removeRecentSearch(searchTerm) },
                                modifier = Modifier.size(24.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "삭제",
                                    tint = Gray700,
                                    modifier = Modifier.size(16.dp),
                                )
                            }
                        }
                    }
                }

                // 추천 검색어 섹션
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "추천 검색어",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                    )
                }
                items(uiState.recommendedSearches) { searchTerm ->
                    TextButton(
                        onClick = {
                            viewModel.updateSearchQuery(searchTerm)
                            viewModel.search(searchTerm)
                        },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = searchTerm,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Primary,
                        )
                    }
                }
            } else {
                // 검색 결과
                if (uiState.isLoading) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            androidx.compose.material3.CircularProgressIndicator()
                        }
                    }
                } else if (uiState.searchResults.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "검색 결과가 없습니다",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Gray700,
                            )
                        }
                    }
                } else {
                    items(uiState.searchResults) { combination ->
                        CombinationCard(
                            combination = combination,
                            onClick = { onCombinationClick(combination.id) },
                            onLikeClick = { /* TODO: 좋아요 기능 */ },
                        )
                    }
                }
            }
        }

        // 검색어 입력 시 자동 검색 (debounce)
        androidx.compose.runtime.LaunchedEffect(uiState.searchQuery) {
            if (uiState.searchQuery.isNotEmpty()) {
                delay(500)
                if (uiState.searchQuery == searchTextFieldValue.value.text) {
                    viewModel.search(uiState.searchQuery)
                }
            }
        }
    }
}
