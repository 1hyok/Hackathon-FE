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

// 담당자: 일혁
// TODO: 사용자 정보 API 연동 필요
@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val repository: CombinationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    init {
        loadMyCombinations()
    }

    fun loadMyCombinations() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            repository.getMyCombinations().fold(
                onSuccess = { combinations ->
                    _uiState.value = _uiState.value.copy(
                        myCombinations = combinations,
                        isLoading = false
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "내 조합을 불러올 수 없습니다"
                    )
                }
            )
        }
    }

    fun refresh() {
        loadMyCombinations()
    }
}

data class MyPageUiState(
    val myCombinations: List<Combination> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

