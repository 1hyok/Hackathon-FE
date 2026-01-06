package com.example.hackathon.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class CombinationResponse(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String? = null,
    val category: String,
    val ingredients: List<String>,
    val steps: List<String>,
    val author: UserResponse,
    val likeCount: Int,
    val createdAt: String
)

