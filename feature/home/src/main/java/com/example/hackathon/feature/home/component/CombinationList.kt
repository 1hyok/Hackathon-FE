package com.example.hackathon.feature.home.component

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.hackathon.core.designsystem.component.CombinationCard
import com.example.hackathon.core.designsystem.theme.Gray700
import com.example.hackathon.core.designsystem.theme.HackathonTheme
import com.example.hackathon.core.model.Combination
import com.example.hackathon.feature.home.R

@Composable
fun CombinationList(
    modifier: Modifier = Modifier,
    results: List<Combination>,
    onCombinationClick: (String) -> Unit = {}
) {
    if (results.isEmpty()) {
        Box(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(top = 40.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.home_search_empty),
                style = HackathonTheme.typography.Body_medium,
                color = Gray700,
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(results) { combination ->
                CombinationCard(
                    combination = combination,
                    onClick = { onCombinationClick(combination.id) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
