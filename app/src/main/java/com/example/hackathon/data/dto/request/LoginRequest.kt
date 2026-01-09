package com.example.hackathon.data.dto.request

import kotlinx.serialization.Serializable

/**
 * 로그인 요청 DTO
 * POST /auth/login
 * 
 * Swagger 스펙에 따라 nickname과 password를 사용
 */
@Serializable
data class LoginRequest(
    val nickname: String,
    val password: String,
)
