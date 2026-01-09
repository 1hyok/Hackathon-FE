package com.example.hackathon.data.dto.response

data class RecipeDetailResponse(
    val id: Long,
    val title: String,
    val category: String,
    val description: String,
    val author: UserResponse,
    val images: List<String>,
    val ingredients: List<IngredientDto>,
    val stats: StatsDto,
    val userInteraction: UserInteractionDto,
    val tags: List<String>,
    val createdAt: String,
    val updatedAt: String,
)

data class IngredientDto(
    val name: String,
    val amount: String,
)

data class StatsDto(
    val likesCount: Int,
)

data class UserInteractionDto(
    val isLiked: Boolean,
    val isMine: Boolean,
)
