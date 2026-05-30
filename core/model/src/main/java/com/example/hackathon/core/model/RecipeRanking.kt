package com.example.hackathon.core.model

data class RecipeRanking(
    val currentCategory: String,
    val list: List<RecipeRankingItem>,
)

data class RecipeRankingItem(
    val title: String,
    val description: String,
    val viewCount: Int,
    val likes: Int,
    val username: String,
    val thumbnailUrl: String? = null,
)
