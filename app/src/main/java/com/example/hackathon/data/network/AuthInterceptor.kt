package com.example.hackathon.data.network

import com.example.hackathon.data.local.TokenManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor
    @Inject
    constructor(
        private val tokenManager: TokenManager,
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()

            // 인증이 필요 없는 엔드포인트는 그대로 진행
            if (isAuthRequired(originalRequest)) {
                val accessToken = runBlocking { tokenManager.getAccessToken() }

                if (accessToken != null) {
                    val authenticatedRequest =
                        originalRequest.newBuilder()
                            .header("Authorization", "Bearer $accessToken")
                            .build()

                    val response = chain.proceed(authenticatedRequest)

                    // 401 Unauthorized 응답 시 원래 응답 반환
                    // 토큰 재발급은 별도로 처리 (순환 의존성 방지)
                    return response
                }
            }

            return chain.proceed(originalRequest)
        }

        private fun isAuthRequired(request: Request): Boolean {
            val path = request.url.encodedPath
            // 인증이 필요 없는 엔드포인트
            return !path.contains("/auth/login") &&
                !path.contains("/auth/signup") &&
                !path.contains("/auth/reissue")
        }

    }
