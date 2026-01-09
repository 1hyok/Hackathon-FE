package com.example.hackathon.data.service

import com.example.hackathon.data.dto.response.BaseResponse
import com.example.hackathon.data.dto.response.RecipeDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeService {
    @GET("recipes/{id}")
    suspend fun getRecipeDetail(
        @Path("id") id: Long,
    ): BaseResponse<RecipeDetailResponse>
}
