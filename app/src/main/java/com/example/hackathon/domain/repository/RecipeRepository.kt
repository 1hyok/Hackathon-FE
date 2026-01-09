package com.example.hackathon.domain.repository

import com.example.hackathon.domain.entity.RecipeDetail
import com.example.hackathon.domain.entity.RecipeRanking

interface RecipeRepository {
    // 1. 전체 랭킹 조회(홈화면)
    suspend fun getRecipeRanking(page: Int? = null): Result<RecipeRanking>

    // 2. 특정 카테고리 랭킹조회
    suspend fun getRecipeRankingByCategory(categoryId: String): Result<RecipeRanking>

    // 3. 검색/해시태그검색
    suspend fun searchRecipes(keyword: String): Result<RecipeRanking>

    // 4. 레시피 상세조회
    suspend fun getRecipeDetail(postId: Long): Result<RecipeDetail>

    // 5. 좋아요
    suspend fun likeRecipe(id: Long): Result<Unit>

    // 6. 좋아요 취소
    suspend fun unlikeRecipe(postId: Long): Result<Unit>
}
