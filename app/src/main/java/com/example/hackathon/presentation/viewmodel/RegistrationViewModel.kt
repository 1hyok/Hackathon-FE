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
class RegistrationViewModel
    @Inject
    constructor() : ViewModel() {
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
                // TODO: API 연동
                // 임시로 성공 처리
                DummyData.currentUser = DummyData.dummyUser
                _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = true)
            }
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
