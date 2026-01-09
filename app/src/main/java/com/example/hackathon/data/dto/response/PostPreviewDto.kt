package com.example.hackathon.data.dto.response

import kotlinx.serialization.Serializable

/**
 * 레시피 목록 조회 시 사용하는 미리보기 DTO
 * GET /recipes 응답에 사용
 */
@Serializable
data class PostPreviewDto(
    val postId: Long,
    val title: String,
    val description: String? = null,
    val thumbnailUrl: String? = null,
    val author: AuthorDto,
    val likesCount: Int = 0,
    val viewCount: Int = 0,
    val createdAt: String,
)

@Serializable
data class AuthorDto(
    val userId: String,
    val nickname: String,
    val profileImageUrl: String? = null,
)
