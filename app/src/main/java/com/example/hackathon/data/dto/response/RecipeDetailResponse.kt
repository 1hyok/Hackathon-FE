package com.example.hackathon.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
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

@Serializable
data class IngredientDto(
    val name: String,
    val amount: String,
)

@Serializable
data class StatsDto(
    val likesCount: Int,
)

@Serializable
data class UserInteractionDto(
    val isLiked: Boolean,
    val isMine: Boolean,
)
