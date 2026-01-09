package com.example.hackathon.presentation.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HashTagInputSection(
    tagInput: String,
    tags: List<String>,
    onTagInputChange: (String) -> Unit,
    onAddTag: (String) -> Unit,
    onRemoveTag: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // 해시태그 입력
        OutlinedTextField(
            value = tagInput,
            onValueChange = { newValue ->
                // 공백, 쉼표, 엔터 입력 시 자동으로 태그 추가
                if (newValue.endsWith(" ") || newValue.endsWith(",") || newValue.endsWith("\n")) {
                    val tagText =
                        newValue.substringBeforeLast(" ")
                            .substringBeforeLast(",")
                            .substringBeforeLast("\n")
                    val normalized = tagText.trim().trimStart('#').trim()
                    if (normalized.isNotBlank() && !tags.contains("#$normalized")) {
                        onAddTag(normalized)
                    }
                    onTagInputChange("")
                } else {
                    // # 입력은 무시 (leadingIcon에 이미 표시됨)
                    val cleanedValue = newValue.replace("#", "")
                    onTagInputChange(cleanedValue)
                }
            },
            placeholder = {
                Text(
                    text = "#입력 후 띄어쓰기 (1개~5개)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = com.example.hackathon.ui.theme.Gray700,
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
            colors =
                androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = com.example.hackathon.ui.theme.Primary,
                    unfocusedBorderColor = com.example.hackathon.ui.theme.Primary.copy(alpha = 0.5f),
                ),
            leadingIcon = {
                Text(
                    text = "#",
                    style = MaterialTheme.typography.bodyLarge,
                    color = com.example.hackathon.ui.theme.Primary,
                    modifier = Modifier.padding(start = 16.dp),
                )
            },
            trailingIcon = {
                if (tagInput.isNotBlank()) {
                    IconButton(
                        onClick = {
                            val normalized = tagInput.trim()
                            if (normalized.isNotBlank() && !tags.contains("#$normalized")) {
                                onAddTag(normalized)
                                onTagInputChange("")
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "태그 추가",
                        )
                    }
                }
            },
        )

        if (tags.isNotEmpty()) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                tags.forEach { tag ->
                    TagChip(
                        text = tag,
                        onRemove = { onRemoveTag(tag) },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HashTagInputSectionPreview() {
    HashTagInputSection(
        tagInput = "",
        tags = listOf("#서브웨이", "#편의점"),
        onTagInputChange = {},
        onAddTag = { },
        onRemoveTag = {},
    )
}
