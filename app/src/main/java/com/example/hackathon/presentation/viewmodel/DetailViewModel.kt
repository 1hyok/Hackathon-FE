package com.example.hackathon.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackathon.domain.entity.RecipeDetail
import com.example.hackathon.domain.repository.CombinationRepository
import com.example.hackathon.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: 디자인 확인 후 UI 조정 필요
@HiltViewModel
class DetailViewModel
    @Inject
    constructor(
        private val repository: CombinationRepository,
        private val recipeRepository: RecipeRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(DetailUiState())
        val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

        fun loadRecipeDetail(id: Long) {
            viewModelScope.launch {
                _uiState.value =
                    _uiState.value.copy(
                        isLoading = true,
                        error = null,
                    )

                repository.getRecipeDetail(id).fold(
                    onSuccess = { result ->
                        _uiState.value =
                            _uiState.value.copy(
                                isLoading = false,
                                recipeDetail = result,
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
            val current = _uiState.value.recipeDetail ?: return

            val isLiked = current.userInteraction.isLiked
            val currentCount = current.stats.likesCount

            // 낙관적 업데이트: UI를 먼저 업데이트
            val updated =
                current.copy(
                    stats =
                        current.stats.copy(
                            likesCount =
                                if (isLiked) currentCount - 1 else currentCount + 1,
                        ),
                    userInteraction =
                        current.userInteraction.copy(
                            isLiked = !isLiked,
                        ),
                )

            _uiState.value =
                _uiState.value.copy(
                    recipeDetail = updated,
                )

            // API 호출
            viewModelScope.launch {
                val result =
                    if (isLiked) {
                        recipeRepository.unlikeRecipe(current.id)
                    } else {
                        recipeRepository.likeRecipe(current.id)
                    }

                result.fold(
                    onSuccess = {
                        // API 호출 성공 - 이미 UI는 업데이트됨
                    },
                    onFailure = { throwable ->
                        // API 호출 실패 시 원래 상태로 롤백
                        _uiState.value =
                            _uiState.value.copy(
                                recipeDetail = current,
                            )
                        _uiState.value =
                            _uiState.value.copy(
                                error = throwable.message ?: "좋아요 처리에 실패했습니다",
                            )
                    },
                )
            }
        }
    }

data class DetailUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val recipeDetail: RecipeDetail? = null,
)
