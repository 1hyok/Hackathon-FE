package com.example.hackathon.data.network

import com.example.hackathon.data.dto.request.ReissueRequest
import com.example.hackathon.data.local.TokenManager
import com.example.hackathon.data.service.AuthService
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * JWT 토큰을 자동으로 추가하고, 401 응답 시 토큰 재발급을 시도하는 Interceptor
 *
 * 토큰 재발급 전략:
 * 1. 모든 요청에 AccessToken을 Authorization 헤더에 추가
 * 2. 401 Unauthorized 응답을 받으면:
 *    - RefreshToken으로 /auth/reissue 호출
 *    - 새 토큰으로 원래 요청을 재시도
 *    - 재발급 실패 시 원래 401 응답 반환
 */
@Singleton
class AuthInterceptor
    @Inject
    constructor(
        private val tokenManager: TokenManager,
        private val authService: AuthService,
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()

            // 인증이 필요 없는 엔드포인트는 그대로 진행
            if (!isAuthRequired(originalRequest)) {
                return chain.proceed(originalRequest)
            }

            // AccessToken 추가
            val accessToken = runBlocking { tokenManager.getAccessToken() }
            val authenticatedRequest = if (accessToken != null) {
                originalRequest.newBuilder()
                    .header("Authorization", "Bearer $accessToken")
                    .build()
            } else {
                originalRequest
            }

            var response = chain.proceed(authenticatedRequest)

            // 401 Unauthorized 응답 시 토큰 재발급 시도
            if (response.code == 401) {
                response.close() // 리소스 정리

                val newAccessToken = runBlocking {
                    reissueToken()
                }

                // 재발급 성공 시 원래 요청을 새 토큰으로 재시도
                if (newAccessToken != null) {
                    val retryRequest = originalRequest.newBuilder()
                        .header("Authorization", "Bearer $newAccessToken")
                        .build()
                    response = chain.proceed(retryRequest)
                }
                // 재발급 실패 시 원래 401 응답을 그대로 반환 (이미 close됨)
            }

            return response
        }

        /**
         * RefreshToken으로 새 AccessToken을 발급받음
         * @return 새 AccessToken, 실패 시 null
         */
        private suspend fun reissueToken(): String? {
            return try {
                val refreshToken = tokenManager.getRefreshToken()
                    ?: return null

                val request = ReissueRequest(refreshToken = refreshToken)
                val reissueResponse = authService.reissue(request)

                // 서버 응답: {"accessToken":"...","refreshToken":"..."} (BaseResponse 래퍼 없음)
                val newAccessToken = reissueResponse.accessToken
                val newRefreshToken = reissueResponse.refreshToken ?: refreshToken

                // 새 토큰 저장
                tokenManager.saveTokens(newAccessToken, newRefreshToken)
                newAccessToken
            } catch (e: Exception) {
                // 재발급 실패 (네트워크 에러, 파싱 에러 등)
                // android.util.Log.e("AuthInterceptor", "Reissue exception", e)
                null
            }
        }

        /**
         * 인증이 필요한 엔드포인트인지 확인
         */
        private fun isAuthRequired(request: Request): Boolean {
            val path = request.url.encodedPath
            // 인증이 필요 없는 엔드포인트
            return !path.contains("/auth/login") &&
                !path.contains("/auth/signup") &&
                !path.contains("/auth/reissue")
        }
    }
