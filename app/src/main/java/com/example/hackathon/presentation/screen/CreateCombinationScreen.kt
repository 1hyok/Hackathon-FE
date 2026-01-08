package com.example.hackathon.presentation.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.hackathon.presentation.viewmodel.CreateCombinationViewModel
import com.example.hackathon.ui.theme.Gray50
import com.example.hackathon.ui.theme.Gray700
import com.example.hackathon.ui.theme.Primary

// 담당자: 일혁
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
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "조합 등록",
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = Primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors =
                    CardDefaults.cardColors(
                        containerColor = Gray50,
                    ),
                shape = RoundedCornerShape(12.dp),
            ) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    // 이미지 선택 및 미리보기
                    if (uiState.imageUri != null) {
                        // 선택한 이미지 미리보기
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(180.dp),
                        ) {
                            AsyncImage(
                                model = uiState.imageUri,
                                contentDescription = "선택한 이미지",
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop,
                            )
                            // 이미지 제거 버튼
                            IconButton(
                                onClick = { viewModel.updateImageUri(null) },
                                modifier =
                                    Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(8.dp),
                            ) {
                                Box(
                                    modifier =
                                        Modifier
                                            .size(32.dp)
                                            .background(
                                                Color.Black.copy(alpha = 0.6f),
                                                CircleShape,
                                            ),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "이미지 제거",
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp),
                                    )
                                }
                            }
                        }
                    } else {
                        // 이미지 선택 버튼
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .background(Gray50, RoundedCornerShape(12.dp))
                                    .border(1.dp, Gray700, RoundedCornerShape(12.dp))
                                    .clickable { imagePickerLauncher.launch("image/*") },
                            contentAlignment = Alignment.Center,
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "이미지 등록",
                                    tint = Gray700,
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "이미지 등록",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Gray700,
                                )
                            }
                        }
                    }

                    // 제목 입력
                    OutlinedTextField(
                        value = uiState.title,
                        onValueChange = viewModel::updateTitle,
                        label = { Text("제목") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )

                    // 해시태그 입력
                    OutlinedTextField(
                        value = tagInput.value,
                        onValueChange = { newValue ->
                            // 공백, 쉼표, 엔터 입력 시 자동으로 태그 추가
                            if (newValue.endsWith(" ") || newValue.endsWith(",") || newValue.endsWith("\n")) {
                                val tagText = newValue.substringBeforeLast(" ").substringBeforeLast(",").substringBeforeLast("\n")
                                val normalized = tagText.trim().trimStart('#').trim()
                                if (normalized.isNotBlank() && !uiState.tags.contains("#$normalized")) {
                                    viewModel.addTag("#$normalized")
                                }
                                tagInput.value = ""
                            } else {
                                // # 입력은 무시 (leadingIcon에 이미 표시됨)
                                val cleanedValue = newValue.replace("#", "")
                                tagInput.value = cleanedValue
                            }
                        },
                        label = { Text("해시태그") },
                        placeholder = { Text("태그를 입력하세요") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        supportingText = { Text("태그 입력 후 공백/쉼표/Enter로 추가") },
                        leadingIcon = {
                            Text(
                                text = "#",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(start = 16.dp),
                            )
                        },
                        trailingIcon = {
                            if (tagInput.value.isNotBlank()) {
                                IconButton(
                                    onClick = {
                                        val normalized = tagInput.value.trim()
                                        if (normalized.isNotBlank() && !uiState.tags.contains("#$normalized")) {
                                            viewModel.addTag("#$normalized")
                                            tagInput.value = ""
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

                    if (uiState.tags.isNotEmpty()) {
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            uiState.tags.forEach { tag ->
                                TagChip(
                                    text = tag,
                                    onRemove = { viewModel.removeTag(tag) },
                                )
                            }
                        }
                    }

                    // 공개 여부 토글
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = "공개 여부",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            // 공개 버튼
                            if (uiState.isPublic) {
                                Button(
                                    onClick = { viewModel.updateIsPublic(true) },
                                    modifier = Modifier.weight(1f),
                                    colors =
                                        ButtonDefaults.buttonColors(
                                            containerColor = Primary,
                                            contentColor = Color.White,
                                        ),
                                ) {
                                    Text("공개")
                                }
                            } else {
                                OutlinedButton(
                                    onClick = { viewModel.updateIsPublic(true) },
                                    modifier = Modifier.weight(1f),
                                    colors =
                                        ButtonDefaults.outlinedButtonColors(
                                            contentColor = Primary,
                                        ),
                                ) {
                                    Text("공개")
                                }
                            }
                            // 비공개 버튼
                            if (!uiState.isPublic) {
                                Button(
                                    onClick = { viewModel.updateIsPublic(false) },
                                    modifier = Modifier.weight(1f),
                                    colors =
                                        ButtonDefaults.buttonColors(
                                            containerColor = Primary,
                                            contentColor = Color.White,
                                        ),
                                ) {
                                    Text("비공개")
                                }
                            } else {
                                OutlinedButton(
                                    onClick = { viewModel.updateIsPublic(false) },
                                    modifier = Modifier.weight(1f),
                                    colors =
                                        ButtonDefaults.outlinedButtonColors(
                                            contentColor = Primary,
                                        ),
                                ) {
                                    Text("비공개")
                                }
                            }
                        }
                    }
                }
            }

            // 재료 입력
            OutlinedTextField(
                value = uiState.ingredients,
                onValueChange = viewModel::updateIngredients,
                label = { Text("재료") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
            )

            // 만드는 방법 입력
            OutlinedTextField(
                value = uiState.steps,
                onValueChange = viewModel::updateSteps,
                label = { Text("만드는 방법") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
            )

            // 설명 입력
            OutlinedTextField(
                value = uiState.description,
                onValueChange = viewModel::updateDescription,
                label = { Text("설명") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
            )

            // 에러 메시지
            uiState.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            // 등록/취소 버튼
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Button(
                    onClick = {
                        viewModel.createCombination {
                            onNavigateBack()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !uiState.isLoading && uiState.title.isNotBlank() && uiState.description.isNotBlank(),
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
                        Text("등록하기")
                    }
                }

                OutlinedButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.weight(1f),
                ) {
                    Text("취소")
                }
            }
        }
    }
}

@Composable
private fun TagChip(
    text: String,
    onRemove: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .background(Primary, RoundedCornerShape(15.dp))
                .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
        )
        FilledTonalIconButton(
            onClick = onRemove,
            modifier =
                Modifier
                    .background(Color.White.copy(alpha = 0.2f), shape = CircleShape)
                    .height(24.dp),
            colors = IconButtonDefaults.filledTonalIconButtonColors(containerColor = Color.Transparent),
            content = {
                Text(
                    text = "X",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )
            },
        )
    }
}
