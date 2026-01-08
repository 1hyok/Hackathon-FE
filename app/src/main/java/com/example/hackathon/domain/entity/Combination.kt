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
    val createdAt: String,
)
