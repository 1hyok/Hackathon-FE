package com.example.hackathon.data.dto.request

import kotlinx.serialization.Serializable

/**
 * 회원가입 요청 DTO
 * POST /auth/signup
 * 
 * Swagger 스펙에 따라 nickname과 password만 사용 (email 불필요)
 */
@Serializable
data class SignupRequest(
    val nickname: String,
    val password: String,
)
