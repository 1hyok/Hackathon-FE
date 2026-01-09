package com.example.hackathon.presentation.screen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hackathon.ui.theme.Gray700
import com.example.hackathon.ui.theme.Primary

data class IngredientItem(
    val name: String,
    val quantity: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientInputSection(
    ingredients: List<IngredientItem>,
    onIngredientNameChange: (Int, String) -> Unit,
    onIngredientQuantityChange: (Int, String) -> Unit,
    onAddIngredient: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // 재료 레이블
        Text(
            text = "재료",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
        )

        // 재료 입력 필드들
        ingredients.forEachIndexed { index, ingredient ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // 재료명 입력
                OutlinedTextField(
                    value = ingredient.name,
                    onValueChange = { onIngredientNameChange(index, it) },
                    placeholder = {
                        Text(
                            text = if (index == 0) "재료명 (예: 땅콩소스)" else "재료명",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Gray700,
                        )
                    },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors =
                        androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = Primary.copy(alpha = 0.5f),
                        ),
                )

                // 용량 입력
                OutlinedTextField(
                    value = ingredient.quantity,
                    onValueChange = { onIngredientQuantityChange(index, it) },
                    placeholder = {
                        Text(
                            text = if (index == 0) "용량 (예: 2스푼)" else "용량",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Gray700,
                        )
                    },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors =
                        androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = Primary.copy(alpha = 0.5f),
                        ),
                )
            }
        }

        // 재료 추가 버튼
        Text(
            text = "재료추가",
            style = MaterialTheme.typography.bodyMedium,
            color = Primary,
            modifier =
                Modifier
                    .padding(start = 4.dp)
                    .clickable { onAddIngredient() },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IngredientInputSectionPreview() {
    IngredientInputSection(
        ingredients =
            listOf(
                IngredientItem("땅콩소스", "2스푼"),
                IngredientItem("굴소스", "1스푼"),
                IngredientItem("", ""),
            ),
        onIngredientNameChange = { _, _ -> },
        onIngredientQuantityChange = { _, _ -> },
        onAddIngredient = {},
    )
}
