package com.example.hackathon.data.service

import com.example.hackathon.data.dto.request.LoginRequest
import com.example.hackathon.data.dto.request.LogoutRequest
import com.example.hackathon.data.dto.request.ReissueRequest
import com.example.hackathon.data.dto.request.SignupRequest
import com.example.hackathon.data.dto.response.BaseResponse
import com.example.hackathon.data.dto.response.LoginResponse
import com.example.hackathon.data.dto.response.ReissueResponse
import com.example.hackathon.data.dto.response.SignupResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    // 1. 회원가입
    @POST("auth/signup")
    suspend fun signup(
        @Body request: SignupRequest,
    ): BaseResponse<SignupResponse>

    // 2. 로그인
    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest,
    ): BaseResponse<LoginResponse>

    // 3. 토큰 재발급
    @POST("auth/reissue")
    suspend fun reissue(
        @Body request: ReissueRequest,
    ): BaseResponse<ReissueResponse>

    // 4. 로그아웃
    @POST("auth/logout")
    suspend fun logout(
        @Body request: LogoutRequest,
    ): BaseResponse<Unit>
}
