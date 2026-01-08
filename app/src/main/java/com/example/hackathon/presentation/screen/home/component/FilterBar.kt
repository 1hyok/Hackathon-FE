package com.example.hackathon.presentation.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.hackathon.R
import com.example.hackathon.core.component.CategoryChip
import com.example.hackathon.domain.entity.Category
import com.example.hackathon.ui.theme.HackathonTheme

@Composable
fun FilterBar(
    categories: List<Category>,
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 20.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_hot),
                contentDescription = "hot",
                modifier = Modifier.size(24.dp),
                tint = HackathonTheme.colors.primary,
            )
            Text(
                text = "꿀조합 랭킹",
                style = HackathonTheme.typography.Sub1_semibold,
                color = HackathonTheme.colors.black,
                modifier = Modifier.padding(start = 4.dp),
            )
        }

        LazyRow(
            modifier =
                modifier
                    .fillMaxWidth()
                    .background(HackathonTheme.colors.white)
                    .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(categories) { category ->
                CategoryChip(
                    category = category,
                    isSelected = category == selectedCategory,
                    onClick = {
                        onCategorySelected(category)
                    },
                )
            }
        }
    }
}
