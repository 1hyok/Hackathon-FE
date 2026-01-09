package com.example.hackathon.data.dto.response

import kotlinx.serialization.Serializable

/**
 * 레시피 상세 조회 응답 DTO
 * GET /recipes/{postId} 응답
 */
@Serializable
data class DetailResponse(
    val postId: Long,
    val title: String,
    val description: String,
    val category: String? = null,
    val author: AuthorDto,
    val images: List<String> = emptyList(),
    val ingredients: List<IngredientDto> = emptyList(),
    val tags: List<String> = emptyList(),
    val likesCount: Int = 0,
    val viewCount: Int = 0,
    val isLiked: Boolean = false,
    val isMine: Boolean = false,
    val createdAt: String,
    val updatedAt: String? = null,
)
