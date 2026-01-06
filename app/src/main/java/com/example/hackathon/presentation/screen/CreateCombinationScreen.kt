package com.example.hackathon.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.hackathon.core.component.CategoryChip
import com.example.hackathon.presentation.viewmodel.CreateCombinationViewModel

// 담당자: 일혁
// TODO: 이미지 업로드 기능 추가 필요
@Composable
fun CreateCombinationScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateCombinationViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // 제목
        Text(
            text = "조합 등록",
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 이미지 업로드 버튼
        Button(
            onClick = { /* TODO: 이미지 업로드 */ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("이미지 업로드")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 제목 입력
        OutlinedTextField(
            value = uiState.title,
            onValueChange = viewModel::updateTitle,
            label = { Text("제목") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 카테고리 선택
        Text(
            text = "카테고리",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            com.example.hackathon.domain.entity.Category.values()
                .filter { it != com.example.hackathon.domain.entity.Category.ALL }
                .forEach { category ->
                    CategoryChip(
                        category = category,
                        isSelected = category == uiState.category,
                        onClick = { viewModel.updateCategory(category) }
                    )
                }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 재료 입력
        OutlinedTextField(
            value = uiState.ingredients,
            onValueChange = viewModel::updateIngredients,
            label = { Text("재료 (쉼표로 구분)") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 만드는 방법 입력
        OutlinedTextField(
            value = uiState.steps,
            onValueChange = viewModel::updateSteps,
            label = { Text("만드는 방법 (줄바꿈으로 구분)") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 5
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 설명 입력
        OutlinedTextField(
            value = uiState.description,
            onValueChange = viewModel::updateDescription,
            label = { Text("설명") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        // 에러 메시지
        if (uiState.error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = uiState.error ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 등록 버튼
        Button(
            onClick = {
                viewModel.createCombination {
                    onNavigateBack()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading && uiState.title.isNotBlank() && uiState.description.isNotBlank()
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("등록하기")
            }
        }
    }
}

