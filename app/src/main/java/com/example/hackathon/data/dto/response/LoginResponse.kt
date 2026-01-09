package com.example.hackathon.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    // 서버 응답에 user 필드가 없을 수 있으므로 optional로 처리
    // 사용자 정보는 /users/mypage 엔드포인트로 별도 조회
    val user: UserResponse? = null,
)
