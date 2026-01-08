package com.example.hackathon.presentation.screen.home.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hackathon.core.component.TopAppLogoBar
import com.example.hackathon.domain.entity.Category
import com.example.hackathon.presentation.screen.home.component.FilterBar
import com.example.hackathon.presentation.screen.home.component.SearchComponent
import com.example.hackathon.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onCombinationClick: (String) -> Unit = {},
    onCreateClick: () -> Unit = {},
) {
    // 최소 UI 유지: 앱이 크래시 되지 않도록 안전한 플레이스홀더만 표시
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var selectedCategory by rememberSaveable {
        mutableStateOf(Category.ALL)
    }

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        TopAppLogoBar()

        SearchComponent(
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        FilterBar(
            modifier = Modifier.padding(top = 20.dp),
            categories = Category.entries.toList(),
            selectedCategory = selectedCategory,
            onCategorySelected = { category ->
                selectedCategory = category
            },
        )

        when (selectedCategory) {
            Category.ALL -> All()
            Category.HAIDILAO -> Haidirao()
            Category.SUBWAY -> Subway()
            Category.CONVENIENCE -> Convenient()
            Category.ETC -> Etc()
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(Modifier, hiltViewModel())
}
