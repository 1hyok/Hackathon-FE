package com.example.hackathon.presentation.screen.combination

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hackathon.core.component.TopAppLogoBar
import com.example.hackathon.presentation.screen.component.HashTagInputSection
import com.example.hackathon.presentation.screen.component.ImageUploadSection
import com.example.hackathon.presentation.screen.component.IngredientInputSection
import com.example.hackathon.presentation.screen.component.VisibilitySelectionSection
import com.example.hackathon.presentation.viewmodel.CreateCombinationViewModel
import com.example.hackathon.ui.theme.Gray700
import com.example.hackathon.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CreateCombinationScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateCombinationViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // 이미지 선택을 위한 ActivityResultLauncher
    val imagePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
        ) { uri: Uri? ->
            uri?.let { viewModel.updateImageUri(it) }
        }

    // 해시태그 입력 UI 상태 (로컬)
    val tagInput = rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color.White)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            // 상단 로고
            TopAppLogoBar()

            // 화면 제목
            Text(
                text = "꿀조합 레시피 등록",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Black,
            )

            // 이미지 업로드 영역
            ImageUploadSection(
                imageUris = uiState.imageUris,
                onImageClick = { imagePickerLauncher.launch("image/*") },
                onRemoveImage = { uri -> viewModel.removeImageUri(uri) },
            )

            // 제목 입력
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "제목",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                )
                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = viewModel::updateTitle,
                    placeholder = {
                        Text(
                            text = "조합 이름을 적어주세요 (최대 15자)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Gray700,
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = Primary.copy(alpha = 0.5f),
                        ),
                )
            }

            // 카테고리 입력
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "카테고리",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                )
                HashTagInputSection(
                    tagInput = tagInput.value,
                    tags = uiState.tags,
                    onTagInputChange = { tagInput.value = it },
                    onAddTag = { normalized ->
                        if (normalized.isNotBlank() && !uiState.tags.contains("#$normalized")) {
                            viewModel.addTag("#$normalized")
                            tagInput.value = ""
                        }
                    },
                    onRemoveTag = { viewModel.removeTag(it) },
                )
            }

            // 재료 입력
            IngredientInputSection(
                ingredients = uiState.ingredientsList,
                onIngredientNameChange = { index, name -> viewModel.updateIngredientName(index, name) },
                onIngredientQuantityChange = { index, quantity -> viewModel.updateIngredientQuantity(index, quantity) },
                onAddIngredient = { viewModel.addIngredient() },
            )

            // 공개 여부 선택
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "공개 여부",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                )
                VisibilitySelectionSection(
                    isPublic = uiState.isPublic,
                    onPublicChange = { viewModel.updateIsPublic(it) },
                )
            }

            // 설명 입력
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "설명",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                )
                OutlinedTextField(
                    value = uiState.description,
                    onValueChange = viewModel::updateDescription,
                    placeholder = {
                        Text(
                            text = "레시피에 대한 설명을 적어주세요 (최대 300자)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Gray700,
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 4,
                    shape = RoundedCornerShape(12.dp),
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = Primary.copy(alpha = 0.5f),
                        ),
                )
            }

            // 에러 메시지
            uiState.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 등록 버튼
            Button(
                onClick = {
                    viewModel.createCombination {
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading && uiState.title.isNotBlank() && uiState.description.isNotBlank(),
                shape = RoundedCornerShape(15.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = Primary,
                        contentColor = Color.White,
                    ),
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.height(16.dp),
                        color = Color.White,
                        strokeWidth = 2.dp,
                    )
                } else {
                    Text(
                        text = "꿀조합 등록하기",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateCombinationScreenPreview() {
    CreateCombinationScreen()
}
