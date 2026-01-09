package com.example.hackathon.data.service

import com.example.hackathon.data.dto.request.LoginRequest
import com.example.hackathon.data.dto.request.LogoutRequest
import com.example.hackathon.data.dto.request.ReissueRequest
import com.example.hackathon.data.dto.request.SignupRequest
import com.example.hackathon.data.dto.response.BaseResponse
import com.example.hackathon.data.dto.response.LoginResponse
import com.example.hackathon.data.dto.response.ReissueResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    // 1. 회원가입
    // 서버 응답: text/plain "가입이 완료되었습니다" (200 OK)
    // 성공 여부는 HTTP 상태 코드로 판단
    @POST("auth/signup")
    suspend fun signup(
        @Body request: SignupRequest,
    ): retrofit2.Response<okhttp3.ResponseBody>

    // 2. 로그인
    // 서버 응답: {"accessToken":"...","refreshToken":"..."} (BaseResponse 래퍼 없음)
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest,
    ): LoginResponse

    /**
     * 토큰 재발급
     * POST /auth/reissue
     *
     * Swagger 스펙: http://13.125.27.133/swagger-ui/index.html#/auth-controller/reissue
     * requestBody: 동적 객체 (일반적으로 refreshToken 필드 사용)
     * response: {"accessToken":"...","refreshToken":"..."} (BaseResponse 래퍼 없음)
     */
    @POST("auth/reissue")
    suspend fun reissue(
        @Body request: ReissueRequest,
    ): ReissueResponse

    // 4. 로그아웃
    @POST("auth/logout")
    suspend fun logout(
        @Body request: LogoutRequest,
    ): BaseResponse<Unit>
}
