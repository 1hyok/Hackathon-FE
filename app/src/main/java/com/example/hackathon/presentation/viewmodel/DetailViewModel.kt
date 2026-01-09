package com.example.hackathon.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackathon.domain.entity.RecipeDetail
import com.example.hackathon.domain.repository.CombinationRepository
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

            // TODO: repository.toggleLike(current.id)
        }
    }

data class DetailUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val recipeDetail: RecipeDetail? = null,
)
