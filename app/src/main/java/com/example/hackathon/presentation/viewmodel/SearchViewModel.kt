package com.example.hackathon.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackathon.domain.repository.CombinationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// 담당자: 일혁
@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val repository: CombinationRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(SearchUiState())
        val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

        init {
            loadRecentSearches()
        }

        fun updateSearchQuery(query: String) {
            _uiState.value = _uiState.value.copy(searchQuery = query)
        }

        fun search(query: String) {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                // TODO: API 연동
                repository.searchCombinations(query).fold(
                    onSuccess = { combinations ->
                        _uiState.value =
                            _uiState.value.copy(
                                searchResults = combinations,
                                isLoading = false,
                            )
                        addRecentSearch(query)
                    },
                    onFailure = {
                        _uiState.value =
                            _uiState.value.copy(
                                isLoading = false,
                                error = it.message,
                            )
                    },
                )
            }
        }

        fun addRecentSearch(query: String) {
            val recent = _uiState.value.recentSearches.toMutableList()
            if (query.isNotBlank() && !recent.contains(query)) {
                recent.add(0, query)
                if (recent.size > 10) {
                    recent.removeAt(recent.size - 1)
                }
                _uiState.value = _uiState.value.copy(recentSearches = recent)
            }
        }

        fun removeRecentSearch(query: String) {
            val recent = _uiState.value.recentSearches.toMutableList()
            recent.remove(query)
            _uiState.value = _uiState.value.copy(recentSearches = recent)
        }

        fun clearRecentSearches() {
            _uiState.value = _uiState.value.copy(recentSearches = emptyList())
        }

        private fun loadRecentSearches() {
            // TODO: SharedPreferences나 DataStore에서 최근 검색어 로드
            _uiState.value = _uiState.value.copy(recentSearches = emptyList())
        }
    }

data class SearchUiState(
    val searchQuery: String = "",
    val recentSearches: List<String> = emptyList(),
    val recommendedSearches: List<String> = listOf("닭갈비", "불백정식", "떡볶이", "치킨"),
    val searchResults: List<com.example.hackathon.domain.entity.Combination> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
