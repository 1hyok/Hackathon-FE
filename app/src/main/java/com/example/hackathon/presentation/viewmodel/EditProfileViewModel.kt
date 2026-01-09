package com.example.hackathon.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackathon.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(EditProfileUiState())
        val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

        init {
            loadProfile()
        }

        fun loadProfile() {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                userRepository.getProfile().fold(
                    onSuccess = { user ->
                        _uiState.value =
                            _uiState.value.copy(
                                nickname = user.nickname ?: "",
                                profileImageUrl = user.profileImageUrl,
                                isLoading = false,
                            )
                    },
                    onFailure = { error ->
                        _uiState.value =
                            _uiState.value.copy(
                                isLoading = false,
                                error = error.message ?: "프로필을 불러올 수 없습니다",
                            )
                    },
                )
            }
        }

        fun updateNickname(nickname: String) {
            _uiState.value = _uiState.value.copy(nickname = nickname, error = null)
        }

        fun saveProfile() {
            val nickname = _uiState.value.nickname.trim()
            if (nickname.isBlank()) {
                _uiState.value = _uiState.value.copy(error = "닉네임을 입력해주세요")
                return
            }

            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isSaving = true, error = null)
                userRepository.updateProfile(
                    nickname = nickname,
                    profileImageUrl = _uiState.value.profileImageUrl,
                ).fold(
                    onSuccess = { user ->
                        _uiState.value =
                            _uiState.value.copy(
                                isSaving = false,
                                isSuccess = true,
                            )
                    },
                    onFailure = { error ->
                        _uiState.value =
                            _uiState.value.copy(
                                isSaving = false,
                                error = error.message ?: "프로필 저장에 실패했습니다",
                            )
                    },
                )
            }
        }
    }

data class EditProfileUiState(
    val nickname: String = "",
    val profileImageUrl: String? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null,
)
