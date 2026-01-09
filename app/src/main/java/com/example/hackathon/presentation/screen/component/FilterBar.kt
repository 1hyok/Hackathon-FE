package com.example.hackathon.presentation.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
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
                    .padding(start = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "hot",
                modifier = Modifier.width(90.dp),
                tint = HackathonTheme.colors.primary,
            )
        }

        LazyRow(
            modifier =
                modifier
                    .fillMaxWidth()
                    .background(HackathonTheme.colors.white)
                    .padding(horizontal = 20.dp),
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
