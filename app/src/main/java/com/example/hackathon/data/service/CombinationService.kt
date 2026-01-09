package com.example.hackathon.data.service

import com.example.hackathon.data.dto.request.CreateCombinationRequest
import com.example.hackathon.data.dto.request.RegisterRequest
import com.example.hackathon.data.dto.request.UpdateCombinationRequest
import com.example.hackathon.data.dto.request.UpdateRegisterRequest
import com.example.hackathon.data.dto.response.BaseResponse
import com.example.hackathon.data.dto.response.CombinationResponse
import retrofit2.Response
import retrofit2.http.*

interface CombinationService {
    @GET("combinations")
    suspend fun getCombinations(
        @Query("category") category: String? = null,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 10,
    ): BaseResponse<List<CombinationResponse>>

    @GET("combinations/{id}")
    suspend fun getCombinationById(
        @Path("id") id: String,
    ): BaseResponse<CombinationResponse>

    @POST("combinations")
    suspend fun createCombination(
        @Body request: CreateCombinationRequest,
    ): BaseResponse<CombinationResponse>

    @PUT("combinations/{id}")
    suspend fun updateCombination(
        @Path("id") id: String,
        @Body request: UpdateCombinationRequest,
    ): BaseResponse<CombinationResponse>

    @DELETE("combinations/{id}")
    suspend fun deleteCombination(
        @Path("id") id: String,
    ): BaseResponse<Unit>

    @GET("users/me/combinations")
    suspend fun getMyCombinations(): BaseResponse<List<CombinationResponse>>

    @POST("combinations/{id}/like")
    suspend fun likeCombination(
        @Path("id") id: String,
    ): BaseResponse<Unit>

    /**
     * 레시피 등록 (Swagger 스펙)
     * POST /register?userId={userId}
     * 
     * 참고: 서버가 200 OK를 반환하지만 빈 응답(Content-Length: 0)을 반환할 수 있음
     * Response<Unit>을 사용하여 빈 응답을 안전하게 처리
     */
    @POST("register")
    suspend fun register(
        @Query("userId") userId: Long,
        @Body request: RegisterRequest,
    ): retrofit2.Response<Unit>

    /**
     * 레시피 수정 (Swagger 스펙)
     * PUT /register/{postId}?userId={userId}
     * 
     * 참고: 서버가 빈 응답을 반환할 수 있음
     * Response<Unit>을 사용하여 빈 응답을 안전하게 처리
     */
    @PUT("register/{postId}")
    suspend fun updateRegister(
        @Path("postId") postId: Long,
        @Query("userId") userId: Long,
        @Body request: UpdateRegisterRequest,
    ): retrofit2.Response<Unit>

    /**
     * 레시피 삭제 (Swagger 스펙)
     * DELETE /register/{postId}?userId={userId}
     * 
     * 참고: 서버가 빈 응답을 반환할 수 있음
     * Response<Unit>을 사용하여 빈 응답을 안전하게 처리
     */
    @DELETE("register/{postId}")
    suspend fun deleteRegister(
        @Path("postId") postId: Long,
        @Query("userId") userId: Long,
    ): retrofit2.Response<Unit>
}
