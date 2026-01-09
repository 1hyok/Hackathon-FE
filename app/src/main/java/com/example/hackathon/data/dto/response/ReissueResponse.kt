package com.example.hackathon.data.dto.response

import kotlinx.serialization.Serializable

/**
 * 토큰 재발급 응답 DTO
 * POST /auth/reissue 응답
 *
 * Swagger 스펙: response가 동적 객체(type: object)로 정의됨
 * 일반적인 JWT 재발급 패턴에 따라 accessToken과 refreshToken 필드를 사용
 * refreshToken은 RTR(Rotation Token Refresh) 방식에 따라 선택사항일 수 있음
 * 실제 서버와 다를 경우 필드명 수정 필요
 */
@Serializable
data class ReissueResponse(
    val accessToken: String,
    val refreshToken: String? = null, // 선택사항 (RTR - Refresh Token Rotation)
)
