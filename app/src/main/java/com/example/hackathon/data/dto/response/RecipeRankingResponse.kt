package com.example.hackathon.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class RecipeRankingResponse(
    val currentCategory: String,
    val list: List<RecipeRankingItemResponse>,
)

@Serializable
data class RecipeRankingItemResponse(
    val title: String,
    val description: String,
    val viewCount: Int,
    val likes: Int,
    val username: String,
    val thumbnailUrl: String? = null,
)
