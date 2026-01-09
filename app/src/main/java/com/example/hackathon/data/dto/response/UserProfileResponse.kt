package com.example.hackathon.data.dto.response

import kotlinx.serialization.Serializable

/**
 * Swagger 스펙에 맞춘 마이페이지 프로필 응답 DTO
 * 실제 서버 응답: {"nickname":"...","myRecipeCount":0}
 */
@Serializable
data class UserProfileResponse(
    val nickname: String,
    val myRecipeCount: Int = 0,
    // 서버 응답에 없을 수 있는 필드들 (optional)
    val id: String? = null,
    val email: String? = null,
    val profileImageUrl: String? = null,
)
