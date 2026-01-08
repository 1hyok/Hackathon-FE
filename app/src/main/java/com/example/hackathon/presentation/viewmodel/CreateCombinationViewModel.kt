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

// 담당자: 일혁
@HiltViewModel
class CreateCombinationViewModel
    @Inject
    constructor(
        private val repository: CombinationRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(CreateCombinationUiState())
        val uiState: StateFlow<CreateCombinationUiState> = _uiState.asStateFlow()

        fun updateTitle(title: String) {
            _uiState.value = _uiState.value.copy(title = title)
        }

        fun updateDescription(description: String) {
            _uiState.value = _uiState.value.copy(description = description)
        }

        fun updateCategory(category: Category) {
            _uiState.value = _uiState.value.copy(category = category)
        }

        fun updateIngredients(ingredients: String) {
            _uiState.value = _uiState.value.copy(ingredients = ingredients)
        }

        fun updateSteps(steps: String) {
            _uiState.value = _uiState.value.copy(steps = steps)
        }

        fun updateIsPublic(isPublic: Boolean) {
            _uiState.value = _uiState.value.copy(isPublic = isPublic)
        }

        fun updateImageUri(uri: android.net.Uri?) {
            _uiState.value = _uiState.value.copy(imageUri = uri)
        }

        fun createCombination(onSuccess: (Combination) -> Unit) {
            val state = _uiState.value

            if (state.title.isBlank() || state.description.isBlank()) {
                _uiState.value = state.copy(error = "제목과 설명을 입력해주세요")
                return
            }

            val ingredientsList =
                state.ingredients.split(",")
                    .map { it.trim() }
                    .filter { it.isNotBlank() }

            val stepsList =
                state.steps.split("\n")
                    .map { it.trim() }
                    .filter { it.isNotBlank() }

            if (ingredientsList.isEmpty() || stepsList.isEmpty()) {
                _uiState.value = state.copy(error = "재료와 만드는 방법을 입력해주세요")
                return
            }

            viewModelScope.launch {
                _uiState.value = state.copy(isLoading = true, error = null)

                repository.createCombination(
                    title = state.title,
                    description = state.description,
                    category = state.category,
                    ingredients = ingredientsList,
                    steps = stepsList,
                    imageUri = state.imageUri,
                ).fold(
                    onSuccess = { combination ->
                        _uiState.value = state.copy(isLoading = false)
                        onSuccess(combination)
                    },
                    onFailure = { error ->
                        _uiState.value =
                            state.copy(
                                isLoading = false,
                                error = error.message ?: "등록에 실패했습니다",
                            )
                    },
                )
            }
        }

        fun clearError() {
            _uiState.value = _uiState.value.copy(error = null)
        }
    }

data class CreateCombinationUiState(
    val title: String = "",
    val description: String = "",
    val category: Category = Category.SUBWAY,
    val ingredients: String = "",
    val steps: String = "",
    val imageUri: android.net.Uri? = null,
    val isPublic: Boolean = true,
    val isLoading: Boolean = false,
    val error: String? = null,
)
