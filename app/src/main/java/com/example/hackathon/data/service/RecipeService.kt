package com.example.hackathon.data.service

import com.example.hackathon.data.dto.response.BaseResponse
import com.example.hackathon.data.dto.response.DetailResponse
import com.example.hackathon.data.dto.response.PostPreviewDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Swagger 스펙에 맞춘 레시피 API 인터페이스
 * recipes-controller 엔드포인트
 */
interface RecipeService {
    /**
     * 레시피 목록 조회 (랭킹)
     * GET /recipes
     * @param page 페이지 번호 (선택)
     * @param category 카테고리 필터 (선택)
     */
    @GET("recipes")
    suspend fun getRecipes(
        @Query("page") page: Int? = null,
        @Query("category") category: String? = null,
    ): BaseResponse<List<PostPreviewDto>>

    /**
     * 레시피 상세 조회
     * GET /recipes/{postId}
     */
    @GET("recipes/{postId}")
    suspend fun getRecipeDetail(
        @Path("postId") postId: Long,
    ): BaseResponse<DetailResponse>

    /**
     * 레시피 검색
     * GET /recipes/search
     * @param keyword 검색 키워드
     */
    @GET("recipes/search")
    suspend fun searchRecipes(
        @Query("keyword") keyword: String,
    ): BaseResponse<List<PostPreviewDto>>

    /**
     * 좋아요 추가
     * POST /recipes/{postId}/likes
     * 서버 응답: 200 OK with empty body (0 bytes)
     */
    @POST("recipes/{postId}/likes")
    suspend fun likeRecipe(
        @Path("postId") postId: Long,
    ): Response<Unit>

    /**
     * 좋아요 취소
     * DELETE /recipes/{postId}/likes
     * 서버 응답: 200 OK with empty body (0 bytes)
     */
    @DELETE("recipes/{postId}/likes")
    suspend fun unlikeRecipe(
        @Path("postId") postId: Long,
    ): Response<Unit>
}
