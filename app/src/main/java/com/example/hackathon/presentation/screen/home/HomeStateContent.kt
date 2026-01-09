package com.example.hackathon.presentation.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hackathon.ui.theme.Gray700
import com.example.hackathon.ui.theme.HackathonTheme

fun LazyListScope.HomeLoadingState() {
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

fun LazyListScope.HomeErrorState(error: String) {
    item {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, bottom = 200.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = error,
                style = HackathonTheme.typography.Body_medium,
                color = Gray700,
                textAlign = TextAlign.Center,
            )
        }
    }
}

fun LazyListScope.HomeEmptyState() {
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

@Preview(showBackground = true, name = "Loading State")
@Composable
private fun HomeLoadingStatePreview() {
    HackathonTheme {
        androidx.compose.foundation.lazy.LazyColumn {
            HomeLoadingState()
        }
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
private fun HomeErrorStatePreview() {
    HackathonTheme {
        androidx.compose.foundation.lazy.LazyColumn {
            HomeErrorState(error = "네트워크 오류가 발생했습니다")
        }
    }
}

@Preview(showBackground = true, name = "Empty State")
@Composable
private fun HomeEmptyStatePreview() {
    HackathonTheme {
        androidx.compose.foundation.lazy.LazyColumn {
            HomeEmptyState()
        }
    }
}
