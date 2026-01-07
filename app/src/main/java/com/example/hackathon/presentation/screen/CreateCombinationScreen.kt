package com.example.hackathon.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hackathon.presentation.viewmodel.CreateCombinationViewModel
import com.example.hackathon.ui.theme.Gray50
import com.example.hackathon.ui.theme.Gray700
import com.example.hackathon.ui.theme.Primary

// 담당자: 일혁
// TODO: 이미지 업로드 기능 추가 필요
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CreateCombinationScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateCombinationViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // 해시태그 입력 UI 상태 (로컬)
    val tagInput = rememberSaveable { mutableStateOf("") }
    val tags = remember { mutableStateListOf<String>() }

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
                    // 이미지 업로드 플레이스홀더
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .background(Gray50, RoundedCornerShape(12.dp))
                                .border(1.dp, Gray700, RoundedCornerShape(12.dp)),
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
                        onValueChange = { tagInput.value = it },
                        label = { Text("해시태그 (예: #서브웨이)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        supportingText = { Text("Enter/쉼표로 추가, X로 삭제") },
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    val normalized =
                                        tagInput.value
                                            .trim()
                                            .trimStart('#')
                                    if (normalized.isNotBlank()) {
                                        tags.add("#$normalized")
                                        tagInput.value = ""
                                    }
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "태그 추가",
                                )
                            }
                        },
                    )

                    if (tags.isNotEmpty()) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            tags.forEach { tag ->
                                TagChip(
                                    text = tag,
                                    onRemove = { tags.remove(tag) },
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
