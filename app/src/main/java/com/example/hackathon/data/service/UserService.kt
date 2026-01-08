package com.example.hackathon.data.service

import com.example.hackathon.data.dto.request.UpdateProfileRequest
import com.example.hackathon.data.dto.response.BaseResponse
import com.example.hackathon.data.dto.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface UserService {
    @GET("user/profile")
    suspend fun getProfile(): BaseResponse<UserResponse>

    @PUT("user/profile")
    suspend fun updateProfile(
        @Body request: UpdateProfileRequest,
    ): BaseResponse<UserResponse>
}
