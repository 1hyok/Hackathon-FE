package com.example.hackathon.domain.entity

data class RecipeDetail(
    val id: Long,
    val title: String,
    val category: String,
    val description: String,
    val author: User,
    val images: List<String>,
    val ingredients: List<Ingredient>,
    val stats: Stats,
    val userInteraction: UserInteraction,
    val tags: List<String>,
    val createdAt: String,
    val updatedAt: String,
)

data class Ingredient(
    val name: String,
    val amount: String,
)

data class Stats(
    val likesCount: Int,
)

data class UserInteraction(
    val isLiked: Boolean,
    val isMine: Boolean,
)
