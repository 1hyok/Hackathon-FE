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

data class SearchUiState(
    val query: String = "",
    val hasSearched: Boolean = false,
    val results: List<Combination> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val repository: CombinationRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(SearchUiState())
        val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

        fun onQueryChange(query: String) {
            _uiState.value =
                _uiState.value.copy(
                    query = query,
                )
        }

        fun onSearch(query: String) {
            if (query.isBlank()) return

            viewModelScope.launch {
                _uiState.value =
                    _uiState.value.copy(
                        hasSearched = true,
                        isLoading = true,
                        error = null,
                        results = emptyList(),
                    )

                repository.searchCombinations(query).fold(
                    onSuccess = { results ->
                        _uiState.value =
                            _uiState.value.copy(
                                results = results,
                                isLoading = false,
                            )
                    },
                    onFailure = { error ->
                        _uiState.value =
                            _uiState.value.copy(
                                isLoading = false,
                                error = error.message ?: "검색 중 오류가 발생했습니다",
                            )
                    },
                )
            }
        }

        fun toggleLike(combinationId: String) {
            viewModelScope.launch {
                repository.likeCombination(combinationId).fold(
                    onSuccess = {
                        // 검색 결과 업데이트
                        val currentResults = _uiState.value.results
                        val updatedResults =
                            currentResults.map { combination ->
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
                                results = updatedResults,
                            )
                    },
                    onFailure = {
                        // 에러 발생 시에도 UI는 업데이트 (낙관적 업데이트)
                        val currentResults = _uiState.value.results
                        val updatedResults =
                            currentResults.map { combination ->
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
                                results = updatedResults,
                            )
                    },
                )
            }
        }
    }
