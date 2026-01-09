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
                _uiState.value =
                    _uiState.value.copy(
                        isLoading = true,
                        error = null,
                    )

                repository.getCombinationById(id).fold(
                    onSuccess = { result ->
                        _uiState.value =
                            _uiState.value.copy(
                                isLoading = false,
                                combination = result,
                            )
                    },
                    onFailure = { throwable ->
                        _uiState.value =
                            _uiState.value.copy(
                                isLoading = false,
                                error = throwable.message ?: "레시피를 불러오지 못했습니다",
                            )
                    },
                )
            }
        }

        fun toggleLike() {
            val current = _uiState.value.combination ?: return

            val isLiked = current.isLiked
            val newLikeCount =
                if (isLiked) {
                    current.likeCount - 1
                } else {
                    current.likeCount + 1
                }

            _uiState.value =
                _uiState.value.copy(
                    combination =
                        current.copy(
                            isLiked = !isLiked,
                            likeCount = newLikeCount,
                        ),
                )

            // TODO: 서버에 좋아요/취소 요청
            // repository.toggleLike(current.id)
        }
    }

data class DetailUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val combination: Combination? = null,
)
