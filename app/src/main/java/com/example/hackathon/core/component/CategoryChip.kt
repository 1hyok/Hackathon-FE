package com.example.hackathon.core.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hackathon.domain.entity.Category

@Composable
fun CategoryChip(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = { Text(category.displayName) },
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors =
            FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            ),
    )
}

@Composable
fun CategoryChipRow(
    categories: List<Category>,
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        categories.forEach { category ->
            CategoryChip(
                category = category,
                isSelected = category == selectedCategory,
                onClick = { onCategorySelected(category) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryChipPreview() {
    CategoryChip(
        category = Category.SUBWAY,
        isSelected = true,
        onClick = {},
    )
}

@Preview(showBackground = true)
@Composable
private fun CategoryChipRowPreview() {
    CategoryChipRow(
        categories = listOf(Category.ALL, Category.SUBWAY, Category.HAIDILAO, Category.CONVENIENCE),
        selectedCategory = Category.SUBWAY,
        onCategorySelected = {},
    )
}
