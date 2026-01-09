package com.example.hackathon.presentation.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.hackathon.presentation.viewmodel.EditProfileViewModel
import com.example.hackathon.ui.theme.Gray700
import com.example.hackathon.ui.theme.HackathonTheme
import com.example.hackathon.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: EditProfileViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onProfileUpdated: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // 저장 성공 시 이전 화면으로 이동
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onProfileUpdated()
            onNavigateBack()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "프로필 수정",
                        style = HackathonTheme.typography.Head2_bold,
                        color = Color.White,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "뒤로가기",
                            tint = Color.White,
                        )
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = Primary,
                    ),
            )
        },
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null && uiState.nickname.isEmpty() -> {
                // 프로필 로드 실패
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = uiState.error ?: "프로필을 불러올 수 없습니다",
                        style = HackathonTheme.typography.Body_medium,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.loadProfile() },
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor = Primary,
                                contentColor = Color.White,
                            ),
                    ) {
                        Text(
                            text = "다시 시도",
                            style = HackathonTheme.typography.Body_medium,
                        )
                    }
                }
            }
            else -> {
                // 정상 상태 - 프로필 수정 폼
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    // 프로필 이미지 영역
                    Box(
                        modifier =
                            Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .background(
                                    if (uiState.profileImageUrl != null) {
                                        Color.Transparent
                                    } else {
                                        Primary.copy(alpha = 0.2f)
                                    },
                                ),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (uiState.profileImageUrl != null) {
                            AsyncImage(
                                model = uiState.profileImageUrl,
                                contentDescription = "프로필 이미지",
                                modifier = Modifier.fillMaxSize(),
                            )
                        } else {
                            val displayText = uiState.nickname.trim().firstOrNull()?.toString() ?: "?"
                            Text(
                                text = displayText,
                                style = HackathonTheme.typography.Head2_bold,
                                color = Color.White,
                            )
                        }
                    }

                    // 닉네임 입력 필드
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text = "닉네임",
                            style = HackathonTheme.typography.Body_medium,
                            color = Color.Black,
                        )
                        OutlinedTextField(
                            value = uiState.nickname,
                            onValueChange = viewModel::updateNickname,
                            enabled = !uiState.isSaving,
                            placeholder = {
                                Text(
                                    text = "닉네임을 입력하세요",
                                    style = HackathonTheme.typography.Body_medium,
                                    color = Gray700,
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(15.dp),
                            colors =
                                OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Primary,
                                    unfocusedBorderColor = Gray700,
                                ),
                        )
                    }

                    // 저장 버튼
                    Button(
                        onClick = { viewModel.saveProfile() },
                        enabled = !uiState.isSaving && uiState.nickname.trim().isNotBlank(),
                        modifier = Modifier.fillMaxWidth(),
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor = Primary,
                                contentColor = Color.White,
                                disabledContainerColor = Gray700.copy(alpha = 0.3f),
                                disabledContentColor = Color.White.copy(alpha = 0.6f),
                            ),
                        shape = RoundedCornerShape(15.dp),
                    ) {
                        if (uiState.isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp,
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                text = "저장 중...",
                                style = HackathonTheme.typography.Body_medium,
                            )
                        } else {
                            Text(
                                text = "저장",
                                style = HackathonTheme.typography.Body_medium,
                            )
                        }
                    }

                    // 에러 메시지
                    if (uiState.error != null && !uiState.isLoading) {
                        Text(
                            text = uiState.error ?: "",
                            style = HackathonTheme.typography.Caption_medium,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditProfileScreenPreview() {
    EditProfileScreen()
}
