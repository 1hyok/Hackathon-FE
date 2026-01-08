package com.example.hackathon.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackathon.domain.entity.Combination
import com.example.hackathon.domain.repository.CombinationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// 담당자: 예원
// TODO: 디자인 확인 후 UI 조정 필요
@HiltViewModel
class DetailViewModel
    @Inject
    constructor(
        private val repository: CombinationRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(DetailUiState())
        val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

        fun loadCombination(id: String) {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)

                repository.getCombinationById(id).fold(
                    onSuccess = { combination ->
                        _uiState.value =
                            _uiState.value.copy(
                                combination = combination,
                                isLoading = false,
                            )
                    },
                    onFailure = { error ->
                        _uiState.value =
                            _uiState.value.copy(
                                isLoading = false,
                                error = error.message ?: "조합을 불러올 수 없습니다",
                            )
                    },
                )
            }
        }

        fun toggleLike() {
            val combination = _uiState.value.combination ?: return
            val wasLiked = combination.isLiked

            viewModelScope.launch {
                repository.likeCombination(combination.id).fold(
                    onSuccess = {
                        // 좋아요 상태와 좋아요 수 업데이트
                        _uiState.value =
                            _uiState.value.copy(
                                combination =
                                    combination.copy(
                                        isLiked = !wasLiked,
                                        likeCount =
                                            if (!wasLiked) {
                                                combination.likeCount + 1
                                            } else {
                                                (combination.likeCount - 1).coerceAtLeast(0)
                                            },
                                    ),
                            )
                    },
                    onFailure = { /* 에러 처리 */ },
                )
            }
        }
    }

data class DetailUiState(
    val combination: Combination? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
