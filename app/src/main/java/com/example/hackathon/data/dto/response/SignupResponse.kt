package com.example.hackathon.data.dto.response

import kotlinx.serialization.Serializable

/**
 * 회원가입 응답 DTO
 * POST /auth/signup 응답
 * 
 * Swagger 스펙에 따라 id와 nickname을 반환 (email 불필요)
 */
@Serializable
data class SignupResponse(
    val id: Long,
    val nickname: String? = null, // 서버 응답에 따라 선택사항
)
