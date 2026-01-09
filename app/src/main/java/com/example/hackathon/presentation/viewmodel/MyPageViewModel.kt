package com.example.hackathon.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackathon.domain.entity.Combination
import com.example.hackathon.domain.entity.User
import com.example.hackathon.domain.repository.CombinationRepository
import com.example.hackathon.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// 담당자: 일혁
@HiltViewModel
class MyPageViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
        private val combinationRepository: CombinationRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(MyPageUiState())
        val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

        init {
            loadProfile()
            loadMyRecipes()
        }

        fun loadProfile() {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true)
                userRepository.getProfile().fold(
                    onSuccess = { user ->
                        _uiState.value =
                            _uiState.value.copy(
                                user = user,
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
            // TODO: 로그아웃 로직 구현
            _uiState.value = _uiState.value.copy(isLoggedOut = true)
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
