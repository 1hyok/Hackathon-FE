package com.example.hackathon.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackathon.data.local.DummyData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// 담당자: 일혁
@HiltViewModel
class LoginViewModel
    @Inject
    constructor() : ViewModel() {
        private val _uiState = MutableStateFlow(LoginUiState())
        val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

        fun updateId(id: String) {
            _uiState.value = _uiState.value.copy(id = id)
        }

        fun updatePassword(password: String) {
            _uiState.value = _uiState.value.copy(password = password)
        }

        fun togglePasswordVisibility() {
            _uiState.value = _uiState.value.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
        }

        fun login() {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                // TODO: API 연동
                // 임시로 성공 처리 - 현재 사용자 정보 저장
                DummyData.currentUser = DummyData.dummyUser
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }

        fun socialLogin(provider: SocialLoginProvider) {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                // TODO: 소셜 로그인 API 연동
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

data class LoginUiState(
    val id: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)

enum class SocialLoginProvider {
    KAKAO,
    NAVER,
    GOOGLE,
}
