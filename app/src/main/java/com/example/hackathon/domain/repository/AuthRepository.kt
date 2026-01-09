package com.example.hackathon.domain.repository

import com.example.hackathon.domain.entity.User

interface AuthRepository {
    // 1. 회원가입
    suspend fun signup(
        email: String,
        password: String,
        nickname: String,
    ): Result<SignupResult>

    // 2. 로그인
    suspend fun login(
        email: String,
        password: String,
    ): Result<LoginResult>

    // 3. 토큰 재발급
    suspend fun reissue(): Result<ReissueResult>

    // 4. 로그아웃
    suspend fun logout(): Result<Unit>

    // 토큰 확인
    suspend fun hasValidTokens(): Boolean
}

data class SignupResult(
    val id: Long,
    val email: String,
)

data class LoginResult(
    val accessToken: String,
    val refreshToken: String,
    val user: User,
)

data class ReissueResult(
    val accessToken: String,
    val refreshToken: String?,
)
