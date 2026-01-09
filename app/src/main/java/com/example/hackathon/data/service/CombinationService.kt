package com.example.hackathon.data.service

import com.example.hackathon.data.dto.request.CreateCombinationRequest
import com.example.hackathon.data.dto.response.BaseResponse
import com.example.hackathon.data.dto.response.CombinationResponse
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

    @GET("users/me/combinations")
    suspend fun getMyCombinations(): BaseResponse<List<CombinationResponse>>

    @POST("combinations/{id}/like")
    suspend fun likeCombination(
        @Path("id") id: String,
    ): BaseResponse<Unit>
}
