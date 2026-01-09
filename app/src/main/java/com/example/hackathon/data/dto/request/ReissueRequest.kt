package com.example.hackathon.data.dto.request

import kotlinx.serialization.Serializable

/**
 * 토큰 재발급 요청 DTO
 * POST /auth/reissue
 *
 * Swagger 스펙: requestBody가 동적 객체(type: object, additionalProperties: string)로 정의됨
 * 일반적인 JWT 재발급 패턴에 따라 refreshToken 필드를 사용
 * 실제 서버와 다를 경우 필드명 수정 필요
 */
@Serializable
data class ReissueRequest(
    val refreshToken: String,
)
