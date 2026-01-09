package com.example.hackathon.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hackathon.core.component.TopAppLogoBar
import com.example.hackathon.presentation.viewmodel.LoginViewModel
import com.example.hackathon.ui.theme.Gray700
import com.example.hackathon.ui.theme.Primary

// 담당자: 일혁
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    onNavigateToRegistration: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // 로그인 성공 시 처리
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onLoginSuccess()
        }
    }

    Scaffold(
        modifier = modifier,
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 로고 영역 (최상단)
            TopAppLogoBar()

            // 태그라인
            Text(
                text = "어디선가 들어본 바로 그 조합 모두 쩝쩝박사에서",
                style = MaterialTheme.typography.bodyMedium,
                color = Gray700,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // 입력 필드 영역
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    // 닉네임 입력 필드
                    OutlinedTextField(
                        value = uiState.id,
                        onValueChange = viewModel::updateId,
                        placeholder = {
                            Text(
                                text = "닉네임",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(15.dp),
                        colors =
                            OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Gray700,
                                unfocusedBorderColor = Gray700,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                            ),
                        singleLine = true,
                    )

                    // Password 입력 필드
                    OutlinedTextField(
                        value = uiState.password,
                        onValueChange = viewModel::updatePassword,
                        placeholder = {
                            Text(
                                text = "Password",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(15.dp),
                        visualTransformation =
                            if (uiState.isPasswordVisible) {
                                VisualTransformation.None
                            } else {
                                PasswordVisualTransformation()
                            },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick = viewModel::togglePasswordVisibility) {
                                Icon(
                                    imageVector =
                                        if (uiState.isPasswordVisible) {
                                            Icons.Default.VisibilityOff
                                        } else {
                                            Icons.Default.Visibility
                                        },
                                    contentDescription =
                                        if (uiState.isPasswordVisible) {
                                            "비밀번호 숨기기"
                                        } else {
                                            "비밀번호 보기"
                                        },
                                    tint = Gray700,
                                )
                            }
                        },
                        colors =
                            OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Gray700,
                                unfocusedBorderColor = Gray700,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                            ),
                        singleLine = true,
                    )
                }

                // 에러 메시지 표시
                uiState.error?.let { error ->
                    Text(
                        text = error,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(horizontal = 4.dp),
                    )
                }

                // 버튼 영역 (가로 배치)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    // 계정생성 버튼
                    Button(
                        onClick = onNavigateToRegistration,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        enabled = !uiState.isLoading,
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor = Primary,
                                contentColor = Color.White,
                                disabledContainerColor = Primary.copy(alpha = 0.5f),
                                disabledContentColor = Color.White.copy(alpha = 0.5f),
                            ),
                    ) {
                        Text(
                            text = "계정생성",
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }

                    // 로그인 버튼
                    Button(
                        onClick = {
                            viewModel.login()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        enabled = uiState.id.isNotBlank() && uiState.password.isNotBlank() && !uiState.isLoading,
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor = Primary,
                                contentColor = Color.White,
                                disabledContainerColor = Primary.copy(alpha = 0.5f),
                                disabledContentColor = Color.White.copy(alpha = 0.5f),
                            ),
                    ) {
                        if (uiState.isLoading) {
                            androidx.compose.material3.CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                            )
                        } else {
                            Text(
                                text = "로그인",
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                    }
                }

                // 하단 문의 안내
                Text(
                    text = "닉네임/비밀번호를 잊어버렸다면? jjupjjup@naver.com으로 문의",
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray700,
                    modifier = Modifier.padding(top = 16.dp),
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}
