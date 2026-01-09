package com.example.hackathon.data.repositoryimpl

import com.example.hackathon.BuildConfig
import com.example.hackathon.data.dto.request.LoginRequest
import com.example.hackathon.data.dto.request.LogoutRequest
import com.example.hackathon.data.dto.request.ReissueRequest
import com.example.hackathon.data.dto.request.SignupRequest
import com.example.hackathon.data.local.DummyData
import com.example.hackathon.data.local.TokenManager
import com.example.hackathon.data.mapper.toEntity
import com.example.hackathon.data.service.AuthService
import com.example.hackathon.domain.entity.User
import com.example.hackathon.domain.repository.AuthRepository
import com.example.hackathon.domain.repository.LoginResult
import com.example.hackathon.domain.repository.ReissueResult
import com.example.hackathon.domain.repository.SignupResult
import kotlinx.coroutines.delay
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
                if (BuildConfig.USE_MOCK_API) {
                    // Mock 모드: 더미 데이터 반환
                    delay(800) // 네트워크 시뮬레이션
                    
                    // 이메일 중복 체크 (간단한 Mock)
                    if (email == "test@test.com") {
                        throw Exception("이미 존재하는 이메일입니다")
                    }
                    
                    // 성공 응답
                    SignupResult(
                        id = System.currentTimeMillis(), // 임시 ID
                        email = email,
                    )
                } else {
                    // 실제 API 호출
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
            }

        // 2. 로그인
        override suspend fun login(
            email: String,
            password: String,
        ): Result<LoginResult> =
            runCatching {
                if (BuildConfig.USE_MOCK_API) {
                    // Mock 모드: 더미 데이터 반환
                    delay(800) // 네트워크 시뮬레이션
                    
                    // 간단한 인증 체크 (Mock)
                    if (password.isEmpty()) {
                        throw Exception("비밀번호를 입력해주세요")
                    }
                    
                    // Mock 토큰 생성
                    val mockAccessToken = "mock_access_token_${System.currentTimeMillis()}"
                    val mockRefreshToken = "mock_refresh_token_${System.currentTimeMillis()}"
                    
                    // 사용자 정보 생성 (닉네임은 이메일에서 추출하거나 기본값 사용)
                    val mockUser = User(
                        id = "user_${System.currentTimeMillis()}",
                        nickname = email.split("@").firstOrNull() ?: "사용자",
                        profileImageUrl = null,
                    )
                    
                    // 토큰 저장
                    tokenManager.saveTokens(mockAccessToken, mockRefreshToken)
                    
                    // DummyData 업데이트
                    DummyData.currentUser = mockUser
                    
                    LoginResult(
                        accessToken = mockAccessToken,
                        refreshToken = mockRefreshToken,
                        user = mockUser,
                    )
                } else {
                    // 실제 API 호출
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
            }

        // 3. 토큰 재발급
        override suspend fun reissue(): Result<ReissueResult> =
            runCatching {
                val refreshToken =
                    tokenManager.getRefreshToken()
                        ?: throw IllegalStateException("Refresh token is null")

                if (BuildConfig.USE_MOCK_API) {
                    // Mock 모드: 새 토큰 생성
                    delay(500)
                    val newAccessToken = "mock_access_token_${System.currentTimeMillis()}"
                    val newRefreshToken = "mock_refresh_token_${System.currentTimeMillis()}"
                    
                    tokenManager.saveTokens(newAccessToken, newRefreshToken)
                    
                    ReissueResult(
                        accessToken = newAccessToken,
                        refreshToken = newRefreshToken,
                    )
                } else {
                    // 실제 API 호출
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
            }

        // 4. 로그아웃
        override suspend fun logout(): Result<Unit> =
            runCatching {
                val refreshToken =
                    tokenManager.getRefreshToken()
                        ?: throw IllegalStateException("Refresh token is null")

                if (BuildConfig.USE_MOCK_API) {
                    // Mock 모드: 토큰만 삭제
                    delay(300)
                    tokenManager.clearTokens()
                    Result.success(Unit)
                } else {
                    // 실제 API 호출
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
            }

        // 토큰 확인
        override suspend fun hasValidTokens(): Boolean {
            return tokenManager.hasTokens()
        }
    }
