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

        fun addIngredientTag(tag: String) {
            val currentTags = _uiState.value.ingredientTags.toMutableList()
            if (!currentTags.contains(tag)) {
                currentTags.add(tag)
                _uiState.value = _uiState.value.copy(ingredientTags = currentTags)
            }
        }

        fun removeIngredientTag(tag: String) {
            val currentTags = _uiState.value.ingredientTags.toMutableList()
            currentTags.remove(tag)
            _uiState.value = _uiState.value.copy(ingredientTags = currentTags)
        }

        fun updateIngredientName(
            index: Int,
            name: String,
        ) {
            val currentIngredients = _uiState.value.ingredientsList.toMutableList()
            if (index < currentIngredients.size) {
                currentIngredients[index] = currentIngredients[index].copy(name = name)
            } else {
                currentIngredients.add(com.example.hackathon.presentation.screen.component.IngredientItem(name, ""))
            }
            _uiState.value = _uiState.value.copy(ingredientsList = currentIngredients)
        }

        fun updateIngredientQuantity(
            index: Int,
            quantity: String,
        ) {
            val currentIngredients = _uiState.value.ingredientsList.toMutableList()
            if (index < currentIngredients.size) {
                currentIngredients[index] = currentIngredients[index].copy(quantity = quantity)
            } else {
                currentIngredients.add(com.example.hackathon.presentation.screen.component.IngredientItem("", quantity))
            }
            _uiState.value = _uiState.value.copy(ingredientsList = currentIngredients)
        }

        fun addIngredient() {
            val currentIngredients = _uiState.value.ingredientsList.toMutableList()
            currentIngredients.add(com.example.hackathon.presentation.screen.component.IngredientItem("", ""))
            _uiState.value = _uiState.value.copy(ingredientsList = currentIngredients)
        }

        fun updateSteps(steps: String) {
            _uiState.value = _uiState.value.copy(steps = steps)
        }

        fun updateIsPublic(isPublic: Boolean) {
            _uiState.value = _uiState.value.copy(isPublic = isPublic)
        }

        fun updateImageUri(uri: android.net.Uri?) {
            val currentUris = _uiState.value.imageUris.toMutableList()
            if (uri != null && currentUris.size < 5) {
                currentUris.add(uri)
                _uiState.value = _uiState.value.copy(imageUris = currentUris)
            }
        }

        fun removeImageUri(uri: android.net.Uri) {
            val currentUris = _uiState.value.imageUris.toMutableList()
            currentUris.remove(uri)
            _uiState.value = _uiState.value.copy(imageUris = currentUris)
        }

        fun addTag(tag: String) {
            val currentTags = _uiState.value.tags.toMutableList()
            if (!currentTags.contains(tag)) {
                currentTags.add(tag)
                _uiState.value = _uiState.value.copy(tags = currentTags)
            }
        }

        fun removeTag(tag: String) {
            val currentTags = _uiState.value.tags.toMutableList()
            currentTags.remove(tag)
            _uiState.value = _uiState.value.copy(tags = currentTags)
        }

        fun createCombination(onSuccess: (Combination) -> Unit) {
            val state = _uiState.value

            if (state.title.isBlank() || state.description.isBlank()) {
                _uiState.value = state.copy(error = "제목과 설명을 입력해주세요")
                return
            }

            // 재료 리스트에서 유효한 재료만 필터링
            val validIngredients =
                state.ingredientsList
                    .filter { it.name.isNotBlank() && it.quantity.isNotBlank() }
                    .map { "${it.name} ${it.quantity}" }

            val stepsList =
                state.steps.split("\n")
                    .map { it.trim() }
                    .filter { it.isNotBlank() }

            if (validIngredients.isEmpty()) {
                _uiState.value = state.copy(error = "재료를 입력해주세요")
                return
            }

            viewModelScope.launch {
                _uiState.value = state.copy(isLoading = true, error = null)

                repository.createCombination(
                    title = state.title,
                    description = state.description,
                    category = state.category,
                    ingredients = validIngredients,
                    steps = stepsList,
                    tags = state.tags,
                    imageUri = state.imageUris.firstOrNull(),
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
    // 재료 리스트 (재료명, 용량 쌍)
    val ingredientsList: List<com.example.hackathon.presentation.screen.component.IngredientItem> =
        listOf(com.example.hackathon.presentation.screen.component.IngredientItem("", "")),
    val steps: String = "",
    val tags: List<String> = emptyList(),
    // 하위 호환성 유지
    val imageUri: android.net.Uri? = null,
    // 최대 5장
    val imageUris: List<android.net.Uri> = emptyList(),
    // 재료 태그 (고소한 맛, 매콤함 등)
    val ingredientTags: List<String> = emptyList(),
    val isPublic: Boolean = true,
    val isLoading: Boolean = false,
    val error: String? = null,
)
