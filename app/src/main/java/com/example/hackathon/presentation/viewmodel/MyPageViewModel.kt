package com.example.hackathon.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackathon.data.local.DummyData
import com.example.hackathon.domain.entity.Combination
import com.example.hackathon.domain.repository.CombinationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// 담당자: 일혁
// TODO: 사용자 정보 API 연동 필요
@HiltViewModel
class MyPageViewModel
    @Inject
    constructor(
        private val repository: CombinationRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(MyPageUiState())
        val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

        private val _selectedTab = MutableStateFlow(MyPageTab.MY_COMBINATIONS)
        val selectedTab: StateFlow<MyPageTab> = _selectedTab.asStateFlow()

        init {
            loadMyCombinations()
        }

        fun selectTab(tab: MyPageTab) {
            _selectedTab.value = tab
            if (tab == MyPageTab.MY_COMBINATIONS) {
                loadMyCombinations()
            } else {
                loadLikedCombinations()
            }
        }

        fun loadLikedCombinations() {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)

                repository.getLikedCombinations().fold(
                    onSuccess = { combinations ->
                        _uiState.value =
                            _uiState.value.copy(
                                likedCombinations = combinations,
                                isLoading = false,
                            )
                    },
                    onFailure = { error ->
                        _uiState.value =
                            _uiState.value.copy(
                                isLoading = false,
                                error = error.message ?: "좋아요한 조합을 불러올 수 없습니다",
                            )
                    },
                )
            }
        }

        fun loadMyCombinations() {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)

                repository.getMyCombinations().fold(
                    onSuccess = { combinations ->
                        _uiState.value =
                            _uiState.value.copy(
                                myCombinations = combinations,
                                isLoading = false,
                            )
                    },
                    onFailure = { error ->
                        _uiState.value =
                            _uiState.value.copy(
                                isLoading = false,
                                error = error.message ?: "내 조합을 불러올 수 없습니다",
                            )
                    },
                )
            }
        }

        fun refresh() {
            if (_selectedTab.value == MyPageTab.MY_COMBINATIONS) {
                loadMyCombinations()
            } else {
                loadLikedCombinations()
            }
        }

        fun toggleLike(combinationId: String) {
            viewModelScope.launch {
                // 서버에 좋아요 상태 동기화 (먼저 실행)
                repository.likeCombination(combinationId).fold(
                    onSuccess = {
                        // 현재 조합의 좋아요 상태 확인
                        val currentCombination =
                            _uiState.value.myCombinations.find { it.id == combinationId }
                                ?: _uiState.value.likedCombinations.find { it.id == combinationId }
                        val wasLiked = currentCombination?.isLiked ?: false

                        // 내가 등록한 조합 목록에서 토글
                        val updatedMyCombinations =
                            _uiState.value.myCombinations.map { combination ->
                                if (combination.id == combinationId) {
                                    combination.copy(
                                        isLiked = !wasLiked,
                                        likeCount =
                                            if (!wasLiked) {
                                                combination.likeCount + 1
                                            } else {
                                                (combination.likeCount - 1).coerceAtLeast(0)
                                            },
                                    )
                                } else {
                                    combination
                                }
                            }

                        // 좋아요한 조합 목록에서도 토글
                        val updatedLikedCombinations =
                            _uiState.value.likedCombinations.map { combination ->
                                if (combination.id == combinationId) {
                                    combination.copy(
                                        isLiked = !wasLiked,
                                        likeCount =
                                            if (!wasLiked) {
                                                combination.likeCount + 1
                                            } else {
                                                (combination.likeCount - 1).coerceAtLeast(0)
                                            },
                                    )
                                } else {
                                    combination
                                }
                            }

                        _uiState.value =
                            _uiState.value.copy(
                                myCombinations = updatedMyCombinations,
                                likedCombinations = updatedLikedCombinations,
                            )

                        // 좋아요 탭이면 목록 새로고침 (좋아요 취소 시 목록에서 제거)
                        if (_selectedTab.value == MyPageTab.LIKED_COMBINATIONS) {
                            loadLikedCombinations()
                        }
                    },
                    onFailure = { /* 에러 처리 */ },
                )
            }
        }

        fun logout() {
            viewModelScope.launch {
                // TODO: 로그아웃 API 호출 및 토큰 삭제
                // 현재 사용자 정보 초기화
                DummyData.currentUser = DummyData.dummyUser
                // TODO: 로그인 화면으로 이동
            }
        }
    }

enum class MyPageTab {
    MY_COMBINATIONS,
    LIKED_COMBINATIONS,
}

data class MyPageUiState(
    val myCombinations: List<Combination> = emptyList(),
    val likedCombinations: List<Combination> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
