package com.example.hackathon.data.service

import com.example.hackathon.data.dto.response.BaseResponse
import com.example.hackathon.data.dto.response.RecipeDetailResponse
import com.example.hackathon.data.dto.response.RecipeRankingResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {
    // 1. 전체 랭킹 조회(홈화면)
    @GET("recipes")
    suspend fun getRecipeRanking(
        @Query("page") page: Int? = null,
    ): BaseResponse<RecipeRankingResponse>

    // 2. 특정 카테고리 랭킹조회
    @GET("recipes")
    suspend fun getRecipeRankingByCategory(
        @Query("category") categoryId: String,
    ): BaseResponse<RecipeRankingResponse>

    // 3. 검색/해시태그검색
    @GET("recipes/search")
    suspend fun searchRecipes(
        @Query("keyword") keyword: String,
    ): BaseResponse<RecipeRankingResponse>

    // 4. 레시피 상세조회
    @GET("recipes/{postId}")
    suspend fun getRecipeDetail(
        @Path("postId") postId: Long,
    ): BaseResponse<RecipeDetailResponse>

    // 5. 좋아요
    @POST("recipes/{id}/likes")
    suspend fun likeRecipe(
        @Path("id") id: Long,
    ): BaseResponse<Unit>

    // 6. 좋아요 취소
    @DELETE("recipes/{postId}/likes")
    suspend fun unlikeRecipe(
        @Path("postId") postId: Long,
    ): BaseResponse<Unit>
}
