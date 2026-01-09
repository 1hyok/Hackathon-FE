package com.example.hackathon.domain.entity

import com.google.common.math.Stats

data class RecipeDetail(
    val id: Int,
    val title: String,
    val category: String,
    val description: String,
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
