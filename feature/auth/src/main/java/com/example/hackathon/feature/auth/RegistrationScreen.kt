package com.example.hackathon.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hackathon.core.designsystem.component.TopAppLogoBar
import com.example.hackathon.core.designsystem.icon.HackathonIcons
import com.example.hackathon.core.designsystem.theme.Gray700
import com.example.hackathon.core.designsystem.theme.HackathonTheme
import com.example.hackathon.core.designsystem.theme.Primary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onRegistrationSuccess: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val nameFieldRequester = remember { BringIntoViewRequester() }
    val passwordFieldRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets.ime
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 로고 영역 (최상단)
            TopAppLogoBar()

            Spacer(modifier = Modifier.height(20.dp))

            // 태그라인
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.auth_tagline_line1),
                    style = HackathonTheme.typography.Body_semibold,
                    color = Gray700,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.auth_tagline_line2),
                    style = HackathonTheme.typography.Body_semibold,
                    color = Gray700,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 닉네임 입력 필드
                Text(
                    text = stringResource(R.string.auth_nickname),
                    style = HackathonTheme.typography.Body_medium,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = uiState.name,
                    onValueChange = viewModel::updateName,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.auth_nickname_hint),
                            style = HackathonTheme.typography.Body_medium,
                            color = Gray700
                        )
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .bringIntoViewRequester(nameFieldRequester)
                            .onFocusChanged { focusState ->
                                if (focusState.isFocused) {
                                    coroutineScope.launch {
                                        delay(300) // 키보드 애니메이션 대기
                                        nameFieldRequester.bringIntoView()
                                    }
                                }
                            },
                    shape = RoundedCornerShape(15.dp),
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = Gray700,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                    textStyle = HackathonTheme.typography.Body_medium,
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 이메일 입력 필드
                Text(
                    text = stringResource(R.string.auth_email),
                    style = HackathonTheme.typography.Body_medium,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = viewModel::updateEmail,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.auth_email_hint),
                            style = HackathonTheme.typography.Body_medium,
                            color = Gray700
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = Gray700,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                    textStyle = HackathonTheme.typography.Body_medium,
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password 입력 필드
                Text(
                    text = stringResource(R.string.auth_password),
                    style = HackathonTheme.typography.Body_medium,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = viewModel::updatePassword,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.auth_password_hint),
                            style = HackathonTheme.typography.Body_medium,
                            color = Gray700
                        )
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .bringIntoViewRequester(passwordFieldRequester)
                            .onFocusChanged { focusState ->
                                if (focusState.isFocused) {
                                    coroutineScope.launch {
                                        delay(300) // 키보드 애니메이션 대기
                                        passwordFieldRequester.bringIntoView()
                                    }
                                }
                            },
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
                                        HackathonIcons.VisibilityOff
                                    } else {
                                        HackathonIcons.Visibility
                                    },
                                contentDescription =
                                    if (uiState.isPasswordVisible) {
                                        stringResource(R.string.auth_password_hide)
                                    } else {
                                        stringResource(R.string.auth_password_show)
                                    },
                                tint = Gray700
                            )
                        }
                    },
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = Gray700,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                    textStyle = HackathonTheme.typography.Body_medium,
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 비밀번호 확인 입력 필드
                Text(
                    text = stringResource(R.string.auth_password_confirm),
                    style = HackathonTheme.typography.Body_medium,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = uiState.passwordConfirm,
                    onValueChange = viewModel::updatePasswordConfirm,
                    placeholder = {
                        Text(
                            text = stringResource(R.string.auth_password_confirm_hint),
                            style = HackathonTheme.typography.Body_medium,
                            color = Gray700
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
                                        HackathonIcons.VisibilityOff
                                    } else {
                                        HackathonIcons.Visibility
                                    },
                                contentDescription =
                                    if (uiState.isPasswordConfirmVisible) {
                                        stringResource(R.string.auth_password_hide)
                                    } else {
                                        stringResource(R.string.auth_password_show)
                                    },
                                tint = Gray700
                            )
                        }
                    },
                    colors =
                        OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = Gray700,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                    textStyle = HackathonTheme.typography.Body_medium,
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 에러 메시지 표시
                uiState.error?.let { error ->
                    Text(
                        text = error,
                        style = HackathonTheme.typography.Caption_medium,
                        color = Color.Red,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // 버튼 영역 (가로 배치)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // 계정생성 버튼
                    Button(
                        onClick = {
                            viewModel.register()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        enabled =
                            uiState.name.isNotBlank() &&
                                uiState.email.isNotBlank() &&
                                uiState.password.isNotBlank() &&
                                uiState.passwordConfirm.isNotBlank() &&
                                !uiState.isLoading,
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor = Primary,
                                contentColor = Color.White,
                                disabledContainerColor = Primary.copy(alpha = 0.5f),
                                disabledContentColor = Color.White.copy(alpha = 0.5f)
                            )
                    ) {
                        if (uiState.isLoading) {
                            androidx.compose.material3.CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White
                            )
                        } else {
                            Text(
                                text = stringResource(R.string.auth_create_account),
                                style = HackathonTheme.typography.Sub1_semibold
                            )
                        }
                    }

                    // 로그인 버튼 (회원가입 화면에서는 뒤로가기 역할)
                    Button(
                        onClick = onNavigateBack,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(15.dp),
                        enabled = !uiState.isLoading,
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor = Gray700,
                                contentColor = Color.White,
                                disabledContainerColor = Gray700.copy(alpha = 0.5f),
                                disabledContentColor = Color.White.copy(alpha = 0.5f)
                            )
                    ) {
                        Text(
                            text = stringResource(R.string.auth_login),
                            style = HackathonTheme.typography.Sub1_semibold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 하단 문의 안내
                Text(
                    text = stringResource(R.string.auth_inquiry_guide),
                    style = HackathonTheme.typography.Caption_medium,
                    color = Gray700,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    // 회원가입 성공 다이얼로그
    if (uiState.isSuccess) {
        AlertDialog(
            onDismissRequest = {
                viewModel.clearSuccess()
                onRegistrationSuccess()
            },
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.auth_success_nickname, uiState.name),
                        style = HackathonTheme.typography.Body_medium,
                        color = Color.Black
                    )
                    Text(
                        text = stringResource(R.string.auth_registration_completed),
                        style = HackathonTheme.typography.Body_medium,
                        color = Color.Black
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.clearSuccess()
                        onRegistrationSuccess()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor = Primary,
                            contentColor = Color.White
                        )
                ) {
                    Text(
                        text = stringResource(R.string.auth_confirm),
                        style = HackathonTheme.typography.Sub1_semibold
                    )
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(15.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegistrationScreenPreview() {
    RegistrationScreen()
}
