package com.example.hackathon.data.repositoryimpl

import com.example.hackathon.data.dto.request.LoginRequest
import com.example.hackathon.data.dto.request.LogoutRequest
import com.example.hackathon.data.dto.request.ReissueRequest
import com.example.hackathon.data.dto.request.SignupRequest
import com.example.hackathon.data.local.TokenManager
import com.example.hackathon.data.mapper.toEntity
import com.example.hackathon.data.service.AuthService
import com.example.hackathon.domain.entity.User
import com.example.hackathon.domain.repository.AuthRepository
import com.example.hackathon.domain.repository.LoginResult
import com.example.hackathon.domain.repository.ReissueResult
import com.example.hackathon.domain.repository.SignupResult
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val authService: AuthService,
        private val tokenManager: TokenManager,
    ) : AuthRepository {
        // 1. 회원가입
        override suspend fun signup(
            email: String,
            password: String,
            nickname: String,
        ): Result<SignupResult> =
            runCatching {
                val request = SignupRequest(email = email, password = password, nickname = nickname)
                val response = authService.signup(request)
                val data =
                    response.data
                        ?: throw IllegalStateException("Signup data is null")
                SignupResult(
                    id = data.id,
                    email = data.email,
                )
            }

        // 2. 로그인
        override suspend fun login(
            email: String,
            password: String,
        ): Result<LoginResult> =
            runCatching {
                val request = LoginRequest(email = email, password = password)
                val response = authService.login(request)
                val data =
                    response.data
                        ?: throw IllegalStateException("Login data is null")

                // 토큰 저장
                tokenManager.saveTokens(data.accessToken, data.refreshToken)

                LoginResult(
                    accessToken = data.accessToken,
                    refreshToken = data.refreshToken,
                    user = data.user.toEntity(),
                )
            }

        // 3. 토큰 재발급
        override suspend fun reissue(): Result<ReissueResult> =
            runCatching {
                val refreshToken =
                    tokenManager.getRefreshToken()
                        ?: throw IllegalStateException("Refresh token is null")

                val request = ReissueRequest(refreshToken = refreshToken)
                val response = authService.reissue(request)
                val data =
                    response.data
                        ?: throw IllegalStateException("Reissue data is null")

                // 새 토큰 저장
                val newRefreshToken = data.refreshToken ?: refreshToken
                tokenManager.saveTokens(data.accessToken, newRefreshToken)

                ReissueResult(
                    accessToken = data.accessToken,
                    refreshToken = data.refreshToken,
                )
            }

        // 4. 로그아웃
        override suspend fun logout(): Result<Unit> =
            runCatching {
                val refreshToken =
                    tokenManager.getRefreshToken()
                        ?: throw IllegalStateException("Refresh token is null")

                val request = LogoutRequest(refreshToken = refreshToken)
                val response = authService.logout(request)

                if (response.code == 200) {
                    // 토큰 삭제
                    tokenManager.clearTokens()
                    Result.success(Unit)
                } else {
                    Result.failure(Exception(response.message))
                }
            }

        // 토큰 확인
        override suspend fun hasValidTokens(): Boolean {
            return tokenManager.hasTokens()
        }
    }
