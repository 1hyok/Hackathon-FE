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
import com.example.hackathon.data.service.UserService
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
        private val userService: UserService,
        private val tokenManager: TokenManager,
    ) : AuthRepository {
        // 1. 회원가입
        override suspend fun signup(
            password: String,
            nickname: String,
        ): Result<SignupResult> =
            runCatching {
                if (BuildConfig.USE_MOCK_API) {
                    // Mock 모드: 더미 데이터 반환
                    delay(800) // 네트워크 시뮬레이션
                    
                    // 닉네임 중복 체크 (간단한 Mock)
                    if (nickname == "test") {
                        throw Exception("이미 존재하는 닉네임입니다")
                    }
                    
                    // 성공 응답
                    SignupResult(
                        id = System.currentTimeMillis(), // 임시 ID
                        nickname = nickname,
                    )
                } else {
                    // 실제 API 호출 (Swagger 스펙: nickname, password만 필요)
                    // 서버 응답: text/plain "가입이 완료되었습니다" (200 OK)
                    val request = SignupRequest(nickname = nickname, password = password)
                    val response = authService.signup(request)
                    
                    if (response.isSuccessful) {
                        // 성공: 서버가 text/plain으로 "가입이 완료되었습니다" 반환
                        // ID는 서버에서 반환하지 않으므로 임시 ID 사용 (실제로는 로그인 후 사용자 정보에서 가져옴)
                        SignupResult(
                            id = System.currentTimeMillis(), // 임시 ID (실제로는 사용하지 않을 수 있음)
                            nickname = nickname,
                        )
                    } else {
                        throw Exception("회원가입 실패: ${response.code()}")
                    }
                }
            }

        // 2. 로그인
        override suspend fun login(
            nickname: String,
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
                    
                    // 사용자 정보 생성
                    val mockUser = User(
                        id = "user_${System.currentTimeMillis()}",
                        nickname = nickname,
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
                    // 실제 API 호출 (Swagger 스펙: nickname, password 사용)
                    val request = LoginRequest(nickname = nickname, password = password)
                    val loginResponse = authService.login(request)

                    // 토큰 저장
                    tokenManager.saveTokens(loginResponse.accessToken, loginResponse.refreshToken)

                    // 사용자 정보 가져오기 (로그인 응답에 user가 없으면 /users/mypage로 조회)
                    val user = if (loginResponse.user != null) {
                        loginResponse.user.toEntity()
                    } else {
                        // 사용자 정보를 별도로 조회
                        // 서버 응답: {"nickname":"...","myRecipeCount":0} (BaseResponse 래퍼 없음)
                        val profile = userService.getMyPage()
                        User(
                            id = profile.id ?: "unknown",
                            nickname = profile.nickname,
                            profileImageUrl = profile.profileImageUrl,
                        )
                    }

                    LoginResult(
                        accessToken = loginResponse.accessToken,
                        refreshToken = loginResponse.refreshToken,
                        user = user,
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
                    // 서버 응답: {"accessToken":"...","refreshToken":"..."} (BaseResponse 래퍼 없음)
                    val request = ReissueRequest(refreshToken = refreshToken)
                    val reissueResponse = authService.reissue(request)

                    // 새 토큰 저장
                    val newRefreshToken = reissueResponse.refreshToken ?: refreshToken
                    tokenManager.saveTokens(reissueResponse.accessToken, newRefreshToken)

                    ReissueResult(
                        accessToken = reissueResponse.accessToken,
                        refreshToken = reissueResponse.refreshToken,
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
                    // 사용자 정보도 초기화
                    DummyData.currentUser = null
                    Result.success(Unit)
                } else {
                    // 실제 API 호출
                    val request = LogoutRequest(refreshToken = refreshToken)
                    val response = authService.logout(request)

                    if (response.code == 200) {
                        // 토큰 삭제
                        tokenManager.clearTokens()
                        // 사용자 정보도 초기화
                        DummyData.currentUser = null
                        Result.success(Unit)
                    } else {
                        // 로그아웃 실패해도 로컬 데이터는 초기화
                        tokenManager.clearTokens()
                        DummyData.currentUser = null
                        Result.failure(Exception(response.message))
                    }
                }
            }

        // 토큰 확인
        override suspend fun hasValidTokens(): Boolean {
            // Mock 모드가 아닐 때만 실제 토큰 확인
            // Mock 모드에서는 자동 로그인 비활성화 (더미 데이터로 자동 로그인 방지)
            if (BuildConfig.USE_MOCK_API) {
                return false
            }
            
            val accessToken = tokenManager.getAccessToken()
            // Mock 토큰이면 무시
            if (accessToken != null && accessToken.startsWith("mock_")) {
                return false
            }
            
            return tokenManager.hasTokens()
        }
    }
