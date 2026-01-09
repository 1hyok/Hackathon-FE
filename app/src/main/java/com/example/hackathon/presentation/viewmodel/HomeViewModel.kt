package com.example.hackathon.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackathon.domain.entity.Category
import com.example.hackathon.domain.entity.Combination
import com.example.hackathon.domain.repository.CombinationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: 디자인 확인 후 UI 조정 필요
@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val repository: CombinationRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(HomeUiState())
        val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

        private val _searchQuery = MutableStateFlow("")
        val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

        private val _selectedCategory = MutableStateFlow(Category.ALL)
        val selectedCategory: StateFlow<Category> = _selectedCategory.asStateFlow()

        init {
            loadCombinations()
        }

        fun loadCombinations() {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)

                val category = if (_selectedCategory.value == Category.ALL) null else _selectedCategory.value
                repository.getCombinations(category).fold(
                    onSuccess = { combinations ->
                        val filtered =
                            if (_searchQuery.value.isBlank()) {
                                combinations
                            } else {
                                combinations.filter {
                                    it.title.contains(_searchQuery.value, ignoreCase = true) ||
                                        it.description.contains(_searchQuery.value, ignoreCase = true)
                                }
                            }
                        _uiState.value =
                            _uiState.value.copy(
                                combinations = filtered,
                                isLoading = false,
                            )
                    },
                    onFailure = { error ->
                        _uiState.value =
                            _uiState.value.copy(
                                isLoading = false,
                                error = error.message ?: "오류가 발생했습니다",
                            )
                    },
                )
            }
        }

        fun updateSearchQuery(query: String) {
            _searchQuery.value = query
            loadCombinations()
        }

        fun selectCategory(category: Category) {
            _selectedCategory.value = category
            loadCombinations()
        }

        fun refresh() {
            loadCombinations()
        }

        fun toggleLike(combinationId: String) {
            viewModelScope.launch {
                repository.likeCombination(combinationId).fold(
                    onSuccess = {
                        // 홈 화면 조합 목록 업데이트
                        val currentCombinations = _uiState.value.combinations
                        val updatedCombinations =
                            currentCombinations.map { combination ->
                                if (combination.id == combinationId) {
                                    val isLiked = combination.isLiked
                                    val newLikeCount =
                                        if (isLiked) {
                                            combination.likeCount - 1
                                        } else {
                                            combination.likeCount + 1
                                        }
                                    combination.copy(
                                        isLiked = !isLiked,
                                        likeCount = newLikeCount,
                                    )
                                } else {
                                    combination
                                }
                            }
                        _uiState.value =
                            _uiState.value.copy(
                                combinations = updatedCombinations,
                            )
                    },
                    onFailure = {
                        // 에러 발생 시에도 UI는 업데이트 (낙관적 업데이트)
                        val currentCombinations = _uiState.value.combinations
                        val updatedCombinations =
                            currentCombinations.map { combination ->
                                if (combination.id == combinationId) {
                                    val isLiked = combination.isLiked
                                    val newLikeCount =
                                        if (isLiked) {
                                            combination.likeCount - 1
                                        } else {
                                            combination.likeCount + 1
                                        }
                                    combination.copy(
                                        isLiked = !isLiked,
                                        likeCount = newLikeCount,
                                    )
                                } else {
                                    combination
                                }
                            }
                        _uiState.value =
                            _uiState.value.copy(
                                combinations = updatedCombinations,
                            )
                    },
                )
            }
        }
    }

data class HomeUiState(
    val combinations: List<Combination> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
