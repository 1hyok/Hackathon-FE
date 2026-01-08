package com.example.hackathon.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hackathon.presentation.viewmodel.LoginViewModel
import com.example.hackathon.presentation.viewmodel.SocialLoginProvider
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
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "로그인",
                        style = MaterialTheme.typography.headlineSmall,
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
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // 로고/타이틀 영역
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = "쩝쩝박사",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Primary,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "맛있는 조합을 찾아보세요",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray700,
                    textAlign = TextAlign.Center,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

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
                        containerColor = Gray700,
                        contentColor = Color.White,
                        disabledContainerColor = Gray700.copy(alpha = 0.5f),
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

            // "또는" 구분선
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier =
                        Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(Gray700.copy(alpha = 0.3f)),
                )
                Text(
                    text = "또는",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray700,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                Box(
                    modifier =
                        Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(Gray700.copy(alpha = 0.3f)),
                )
            }

            // 회원가입 버튼
            OutlinedButton(
                onClick = { /* TODO: 회원가입 화면으로 이동 */ },
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

            // "간편 로그인" 구분선
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier =
                        Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(Gray700.copy(alpha = 0.3f)),
                )
                Text(
                    text = "간편 로그인",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray700,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                Box(
                    modifier =
                        Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(Gray700.copy(alpha = 0.3f)),
                )
            }

            // 소셜 로그인 버튼들
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                // 카카오 로그인
                SocialLoginButton(
                    text = "카카오 로그인",
                    backgroundColor = Color(0xFFFEE500),
                    textColor = Color(0xFFABABAB),
                    onClick = { viewModel.socialLogin(SocialLoginProvider.KAKAO) },
                )

                // 네이버 로그인
                SocialLoginButton(
                    text = "네이버 로그인",
                    backgroundColor = Color(0xFF03C75A),
                    textColor = Color.White,
                    onClick = { viewModel.socialLogin(SocialLoginProvider.NAVER) },
                )

                // 구글 로그인
                SocialLoginButton(
                    text = "구글 로그인",
                    backgroundColor = Color.White,
                    textColor = Gray700,
                    borderColor = Gray700,
                    onClick = { viewModel.socialLogin(SocialLoginProvider.GOOGLE) },
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SocialLoginButton(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    borderColor: Color? = null,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = textColor,
            ),
        border =
            borderColor?.let {
                androidx.compose.foundation.BorderStroke(1.dp, it)
            },
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
