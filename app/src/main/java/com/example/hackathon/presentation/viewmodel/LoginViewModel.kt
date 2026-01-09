package com.example.hackathon.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackathon.data.local.DummyData
import com.example.hackathon.domain.entity.User
import com.example.hackathon.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : ViewModel() {
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

        fun toggleAutoLogin() {
            _uiState.value = _uiState.value.copy(isAutoLogin = !_uiState.value.isAutoLogin)
        }

        fun login() {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null, isSuccess = false)

                // 입력 검증
                if (_uiState.value.id.isBlank() || _uiState.value.password.isBlank()) {
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            error = "이메일과 비밀번호를 입력해주세요",
                        )
                    return@launch
                }

                // API 호출
                authRepository.login(_uiState.value.id, _uiState.value.password).fold(
                    onSuccess = { loginResult ->
                        // 사용자 정보 저장
                        DummyData.currentUser = loginResult.user
                        _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = true)
                    },
                    onFailure = { throwable ->
                        _uiState.value =
                            _uiState.value.copy(
                                isLoading = false,
                                error = throwable.message ?: "로그인에 실패했습니다",
                            )
                    },
                )
            }
        }

        fun socialLogin(provider: SocialLoginProvider) {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                // TODO: 소셜 로그인 API 연동
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }

        fun clearError() {
            _uiState.value = _uiState.value.copy(error = null)
        }
    }

data class LoginUiState(
    val id: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isAutoLogin: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
)

enum class SocialLoginProvider {
    KAKAO,
    NAVER,
    GOOGLE,
}
