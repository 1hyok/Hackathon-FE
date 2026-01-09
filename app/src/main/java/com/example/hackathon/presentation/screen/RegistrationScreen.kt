package com.example.hackathon.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.hackathon.presentation.viewmodel.RegistrationViewModel
import com.example.hackathon.ui.theme.Gray700
import com.example.hackathon.ui.theme.Primary

// 담당자: 일혁
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onRegistrationSuccess: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "회원가입",
                        color = Color.White,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                // 이름 입력 필드
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = viewModel::updateName,
                    placeholder = {
                        Text(
                            text = "이름",
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

                // 아이디 입력 필드
                OutlinedTextField(
                    value = uiState.id,
                    onValueChange = viewModel::updateId,
                    placeholder = {
                        Text(
                            text = "아이디",
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

                // 이메일 입력 필드
                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = viewModel::updateEmail,
                    placeholder = {
                        Text(
                            text = "이메일",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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

                // 비밀번호 확인 입력 필드
                OutlinedTextField(
                    value = uiState.passwordConfirm,
                    onValueChange = viewModel::updatePasswordConfirm,
                    placeholder = {
                        Text(
                            text = "비밀번호 확인",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    visualTransformation =
                        if (uiState.isPasswordConfirmVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = viewModel::togglePasswordConfirmVisibility) {
                            Icon(
                                imageVector =
                                    if (uiState.isPasswordConfirmVisible) {
                                        Icons.Default.VisibilityOff
                                    } else {
                                        Icons.Default.Visibility
                                    },
                                contentDescription =
                                    if (uiState.isPasswordConfirmVisible) {
                                        "비밀번호 확인 숨기기"
                                    } else {
                                        "비밀번호 확인 보기"
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

                // 회원가입 버튼
                Button(
                    onClick = {
                        viewModel.register()
                        if (uiState.isSuccess) {
                            onRegistrationSuccess()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    enabled =
                        uiState.name.isNotBlank() &&
                            uiState.id.isNotBlank() &&
                            uiState.email.isNotBlank() &&
                            uiState.password.isNotBlank() &&
                            uiState.passwordConfirm.isNotBlank() &&
                            !uiState.isLoading,
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
                            text = "회원가입",
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
