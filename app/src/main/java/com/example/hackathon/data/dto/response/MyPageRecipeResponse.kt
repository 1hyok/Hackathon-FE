package com.example.hackathon.data.dto.response

import kotlinx.serialization.Serializable

/**
 * 마이페이지에서 보여줄 레시피 목록 응답 DTO
 * 내가 작성한 레시피 / 내가 좋아요한 레시피 공통 사용
 */
@Serializable
data class MyPageRecipeResponse(
    val postId: Long,
    val title: String,
    val description: String? = null,
    val thumbnailUrl: String? = null,
    val likesCount: Int = 0,
    val viewCount: Int = 0,
    val createdAt: String,
)
