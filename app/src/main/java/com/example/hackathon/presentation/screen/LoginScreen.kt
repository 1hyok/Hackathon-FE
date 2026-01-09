package com.example.hackathon.presentation.screen

import androidx.compose.foundation.border
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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

    Scaffold(
        modifier = modifier,
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
        ) {
            // 로고 영역
            TopAppLogoBar()

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
                    // 아이디/이메일 입력 필드
                    OutlinedTextField(
                        value = uiState.id,
                        onValueChange = viewModel::updateId,
                        placeholder = {
                            Text(
                                text = "아이디 또는 이메일",
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

                    // 비밀번호 입력 필드
                    OutlinedTextField(
                        value = uiState.password,
                        onValueChange = viewModel::updatePassword,
                        placeholder = {
                            Text(
                                text = "비밀번호",
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

                // 자동 로그인 체크박스
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = uiState.isAutoLogin,
                        onCheckedChange = { viewModel.toggleAutoLogin() },
                        colors =
                            CheckboxDefaults.colors(
                                checkedColor = Primary,
                            ),
                    )
                    Text(
                        text = "자동 로그인",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gray700,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }

                // 아이디 찾기 / 비밀번호 찾기 링크
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextButton(
                        onClick = { /* TODO: 아이디 찾기 */ },
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                    ) {
                        Text(
                            text = "아이디 찾기",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Primary,
                        )
                    }
                    Text(
                        text = "|",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gray700,
                        modifier = Modifier.padding(horizontal = 4.dp),
                    )
                    TextButton(
                        onClick = { /* TODO: 비밀번호 찾기 */ },
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp),
                    ) {
                        Text(
                            text = "비밀번호 찾기",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Primary,
                        )
                    }
                }

                // 로그인 버튼
                Button(
                    onClick = {
                        viewModel.login()
                        // TODO: 로그인 성공 시 onLoginSuccess 호출
                    },
                    modifier = Modifier.fillMaxWidth(),
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

                // 회원가입 버튼
                OutlinedButton(
                    onClick = onNavigateToRegistration,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    colors =
                        ButtonDefaults.outlinedButtonColors(
                            contentColor = Primary,
                        ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Primary),
                ) {
                    Text(
                        text = "회원가입",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
