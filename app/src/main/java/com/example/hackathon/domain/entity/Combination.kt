package com.example.hackathon.domain.entity

data class Combination(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val category: Category,
    val ingredients: List<String>,
    val steps: List<String>,
    val tags: List<String> = emptyList(),
    val author: User,
    val likeCount: Int,
    // 현재 사용자가 좋아요를 눌렀는지 여부
    val isLiked: Boolean = false,
    val createdAt: String,
)
