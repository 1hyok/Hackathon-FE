package com.example.hackathon.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.hackathon.domain.entity.Combination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class SearchUiState(
    val query: String = "",
    val hasSearched: Boolean = false,
    val results: List<Combination> = emptyList(),
)

@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        // TODO: repository 추가 예정
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(SearchUiState())
        val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

        fun onQueryChange(query: String) {
            _uiState.value =
                _uiState.value.copy(
                    query = query,
                )
        }

        fun onSearch(query1: String) {
            val query = _uiState.value.query
            if (query.isBlank()) return

            _uiState.value =
                _uiState.value.copy(
                    hasSearched = true,
                    results = emptyList(),
                )
        }
    }
