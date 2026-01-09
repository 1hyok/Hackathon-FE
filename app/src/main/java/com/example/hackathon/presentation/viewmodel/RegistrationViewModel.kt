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
class RegistrationViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(RegistrationUiState())
        val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

        fun updateName(name: String) {
            _uiState.value = _uiState.value.copy(name = name)
        }

        fun updateId(id: String) {
            _uiState.value = _uiState.value.copy(id = id)
        }

        fun updateEmail(email: String) {
            _uiState.value = _uiState.value.copy(email = email)
        }

        fun updatePassword(password: String) {
            _uiState.value = _uiState.value.copy(password = password)
        }

        fun updatePasswordConfirm(passwordConfirm: String) {
            _uiState.value = _uiState.value.copy(passwordConfirm = passwordConfirm)
        }

        fun togglePasswordVisibility() {
            _uiState.value = _uiState.value.copy(isPasswordVisible = !_uiState.value.isPasswordVisible)
        }

        fun togglePasswordConfirmVisibility() {
            _uiState.value = _uiState.value.copy(isPasswordConfirmVisible = !_uiState.value.isPasswordConfirmVisible)
        }

        fun register() {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)

                // 입력 검증
                if (_uiState.value.name.isBlank() || _uiState.value.email.isBlank() || _uiState.value.password.isBlank()) {
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            error = "모든 필드를 입력해주세요",
                        )
                    return@launch
                }

                if (_uiState.value.password != _uiState.value.passwordConfirm) {
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            error = "비밀번호가 일치하지 않습니다",
                        )
                    return@launch
                }

                // API 호출
                authRepository.signup(
                    email = _uiState.value.email,
                    password = _uiState.value.password,
                    nickname = _uiState.value.name,
                ).fold(
                    onSuccess = {
                        _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = true)
                    },
                    onFailure = { throwable ->
                        _uiState.value =
                            _uiState.value.copy(
                                isLoading = false,
                                error = throwable.message ?: "회원가입에 실패했습니다",
                            )
                    },
                )
            }
        }

        fun clearSuccess() {
            _uiState.value = _uiState.value.copy(isSuccess = false)
        }
    }

data class RegistrationUiState(
    val name: String = "",
    val id: String = "",
    val email: String = "",
    val password: String = "",
    val passwordConfirm: String = "",
    val isPasswordVisible: Boolean = false,
    val isPasswordConfirmVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
)
