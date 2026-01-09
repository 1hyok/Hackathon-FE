package com.example.hackathon.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackathon.domain.entity.Combination
import com.example.hackathon.domain.entity.User
import com.example.hackathon.domain.repository.AuthRepository
import com.example.hackathon.domain.repository.CombinationRepository
import com.example.hackathon.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
        private val combinationRepository: CombinationRepository,
        private val authRepository: AuthRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(MyPageUiState())
        val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

        init {
            // 로그인 상태 확인 후 프로필 로드
            viewModelScope.launch {
                val hasTokens = authRepository.hasValidTokens()
                if (hasTokens) {
                    loadProfile()
                    loadMyRecipes()
                } else {
                    // 로그인 안 됨: isLoading을 false로 설정하여 UI가 로그인 화면으로 리다이렉트할 수 있도록
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        user = null,
                    )
                }
            }
        }

        fun loadProfile() {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                userRepository.getProfile().fold(
                    onSuccess = { user ->
                        _uiState.value =
                            _uiState.value.copy(
                                user = user,
                                isLoading = false,
                                error = null,
                            )
                    },
                    onFailure = { error ->
                        // 프로필 로드 실패: 에러만 표시하고 user는 유지 (이전 사용자 정보가 있으면 계속 표시)
                        // 단, 인증 에러(401, 403)인 경우에만 로그아웃 처리
                        val errorMessage = error.message ?: "프로필을 불러올 수 없습니다"
                        val isAuthError = errorMessage.contains("401", ignoreCase = true) ||
                            errorMessage.contains("403", ignoreCase = true) ||
                            errorMessage.contains("Unauthorized", ignoreCase = true) ||
                            errorMessage.contains("Forbidden", ignoreCase = true)
                        
                        if (isAuthError) {
                            // 인증 에러: 로그아웃 처리
                            _uiState.value = _uiState.value.copy(
                                user = null,
                                isLoading = false,
                                error = errorMessage,
                                isLoggedOut = true,
                            )
                        } else {
                            // 기타 에러: 에러만 표시하고 user는 유지
                            _uiState.value = _uiState.value.copy(
                                isLoading = false,
                                error = errorMessage,
                            )
                        }
                    },
                )
            }
        }

        fun loadMyRecipes() {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoadingMyRecipes = true)
                combinationRepository.getMyCombinations().fold(
                    onSuccess = { combinations ->
                        _uiState.value =
                            _uiState.value.copy(
                                myRecipes = combinations,
                                isLoadingMyRecipes = false,
                            )
                    },
                    onFailure = { error ->
                        _uiState.value =
                            _uiState.value.copy(
                                isLoadingMyRecipes = false,
                                error = error.message,
                            )
                    },
                )
            }
        }

        fun loadLikedCombinations() {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoadingLikedCombinations = true)
                combinationRepository.getLikedCombinations().fold(
                    onSuccess = { combinations ->
                        _uiState.value =
                            _uiState.value.copy(
                                likedCombinations = combinations,
                                isLoadingLikedCombinations = false,
                            )
                    },
                    onFailure = { error ->
                        _uiState.value =
                            _uiState.value.copy(
                                isLoadingLikedCombinations = false,
                                error = error.message,
                            )
                    },
                )
            }
        }

        fun selectTab(tab: MyPageTab) {
            _uiState.value = _uiState.value.copy(selectedTab = tab)
            when (tab) {
                MyPageTab.MY_RECIPES -> {
                    if (_uiState.value.myRecipes.isEmpty() && !_uiState.value.isLoadingMyRecipes) {
                        loadMyRecipes()
                    }
                }
                MyPageTab.LIKED_COMBINATIONS -> {
                    if (_uiState.value.likedCombinations.isEmpty() && !_uiState.value.isLoadingLikedCombinations) {
                        loadLikedCombinations()
                    }
                }
            }
        }

        fun logout() {
            viewModelScope.launch {
                authRepository.logout().fold(
                    onSuccess = {
                        // 로그아웃 성공: UI 상태 초기화
                        _uiState.value = _uiState.value.copy(
                            isLoggedOut = true,
                            user = null, // 사용자 정보 초기화
                        )
                    },
                    onFailure = { error ->
                        // 로그아웃 실패해도 로컬 토큰은 삭제하고 로그아웃 처리
                        _uiState.value = _uiState.value.copy(
                            isLoggedOut = true,
                            user = null, // 사용자 정보 초기화
                        )
                    },
                )
            }
        }
    }

enum class MyPageTab {
    MY_RECIPES,
    LIKED_COMBINATIONS,
}

data class MyPageUiState(
    val user: User? = null,
    val myRecipes: List<Combination> = emptyList(),
    val likedCombinations: List<Combination> = emptyList(),
    val selectedTab: MyPageTab = MyPageTab.MY_RECIPES,
    val isLoading: Boolean = false,
    val isLoadingMyRecipes: Boolean = false,
    val isLoadingLikedCombinations: Boolean = false,
    val error: String? = null,
    val isLoggedOut: Boolean = false,
)
