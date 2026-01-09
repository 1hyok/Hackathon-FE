package com.example.hackathon.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.hackathon.core.component.CombinationCard
import com.example.hackathon.domain.entity.Combination
import com.example.hackathon.ui.theme.Gray700
import com.example.hackathon.ui.theme.HackathonTheme

@Composable
fun CombinationList(
    modifier: Modifier = Modifier,
    results: List<Combination>,
    onCombinationClick: (String) -> Unit = {},
    onLikeClick: ((String) -> Unit)? = null,
) {
    if (results.isEmpty()) {
        Box(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(top = 40.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "검색 결과가 없어요 ㅠㅠ",
                style = HackathonTheme.typography.Body_medium,
                color = Gray700,
                textAlign = TextAlign.Center,
            )
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(results) { combination ->
                CombinationCard(
                    combination = combination,
                    onClick = { onCombinationClick(combination.id) },
                    onLikeClick = onLikeClick?.let { { it(combination.id) } },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
